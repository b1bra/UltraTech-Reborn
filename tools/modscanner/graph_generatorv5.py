import os
import json
import networkx as nx
import matplotlib.pyplot as plt


VERSION = "5"


try:
    from logger import get_logger

    logger = get_logger()

    LOGGER_ENABLED = True

except Exception:

    LOGGER_ENABLED = False


    class FallbackLogger:

        def info(self, text):
            print(text)

        def debug(self, text):
            print(text)

        def success(self, text):
            print(text)

        def warning(self, text):
            print(text)

        def error(self, text):
            print(text)

        def exception(self, error):
            print(error)


    logger = FallbackLogger()



def log(text):

    message = f"[GraphGenerator v{VERSION}] {text}"

    logger.info(
        message
    )




def find_paths():

    current = os.path.dirname(
        os.path.abspath(__file__)
    )


    root = os.path.dirname(
        os.path.dirname(current)
    )


    docs = os.path.join(
        root,
        "docs",
        "generated"
    )


    os.makedirs(
        docs,
        exist_ok=True
    )


    return (

        os.path.join(
            docs,
            "mod_graph.json"
        ),

        os.path.join(
            docs,
            "full_graph.png"
        ),

        os.path.join(
            docs,
            "full_graph_labeled.png"
        ),

        os.path.join(
            docs,
            "migration_overview.png"
        )
    )




def get_color(status):

    status = str(status).lower()


    if status in [
        "hard",
        "requires rewrite"
    ]:

        return "red"



    if status in [
        "normal",
        "needs adaptation"
    ]:

        return "orange"



    if status in [
        "easy",
        "simple migration"
    ]:

        return "green"



    return "gray"





def get_node_size(mod):

    size = mod.get(
        "size_mb",
        1
    )


    files = mod.get(
        "files",
        0
    )


    score = mod.get(
        "score",
        0
    )


    value = (

        700
        +
        size * 80
        +
        files / 10
        +
        score * 5

    )


    return min(
        max(
            value,
            700
        ),
        7000
    )





def load_database(path):

    if not os.path.exists(path):

        raise Exception(
            "mod_graph.json not found"
        )


    logger.info(
        f"[GraphGenerator v{VERSION}] Loading: {path}"
    )


    with open(
        path,
        "r",
        encoding="utf-8"
    ) as file:

        data = json.load(
            file
        )


    logger.success(
        f"[GraphGenerator v{VERSION}] Mods loaded: {len(data)}"
    )


    return data





def build_graph(mods):

    graph = nx.DiGraph()


    log(
        "Building graph..."
    )


    for index, mod in enumerate(
        mods,
        1
    ):


        name = mod.get(
            "name",
            "Unknown"
        )


        log(
            f"[{index}/{len(mods)}] {name}"
        )


        graph.add_node(

            name,

            color=get_color(

                mod.get(
                    "difficulty",
                    "Unknown"
                )

            ),

            size=get_node_size(
                mod
            ),

            score=mod.get(
                "score",
                0
            )

        )




    for mod in mods:


        name = mod.get(
            "name",
            "Unknown"
        )


        for dependency in mod.get(
            "dependencies",
            []
        ):


            if dependency not in graph.nodes:


                graph.add_node(

                    dependency,

                    color="gray",

                    size=800,

                    score=0

                )



            graph.add_edge(

                dependency,

                name

            )



    return graph





def get_graph_style(graph):

    colors = []

    sizes = []


    for node in graph.nodes:


        colors.append(

            graph.nodes[node].get(

                "color",

                "gray"

            )

        )


        sizes.append(

            graph.nodes[node].get(

                "size",

                800

            )

        )


    return colors, sizes






def draw_graph(
        graph,
        output,
        labels=True,
        title="UltraTech Migration Dependency Graph"
):


    if len(graph.nodes) == 0:

        raise Exception(
            "Graph is empty"
        )



    logger.info(

        f"[GraphGenerator v{VERSION}] Creating {os.path.basename(output)}"

    )


    colors, sizes = get_graph_style(
        graph
    )


    logger.debug(
        "Calculating layout..."
    )


    position = nx.spring_layout(

        graph,

        k=1.5,

        iterations=200

    )



    plt.figure(

        figsize=(

            30,

            22

        )

    )



    nx.draw_networkx_nodes(

        graph,

        position,

        node_color=colors,

        node_size=sizes,

        alpha=0.85

    )



    nx.draw_networkx_edges(

        graph,

        position,

        arrows=True,

        alpha=0.35

    )



    if labels:


        nx.draw_networkx_labels(

            graph,

            position,

            font_size=8

        )



    plt.title(
        title
    )


    plt.axis(
        "off"
    )


    plt.savefig(

        output,

        dpi=300,

        bbox_inches="tight"

    )


    plt.close()



    logger.success(

        f"[GraphGenerator v{VERSION}] Created: {output}"

    )






def generate_full_graph(graph, output):

    draw_graph(

        graph,

        output,

        labels=False,

        title="UltraTech Full Migration Graph"

    )






def generate_full_graph_labeled(graph, output):

    draw_graph(

        graph,

        output,

        labels=True,

        title="UltraTech Full Migration Graph (Labeled)"

    )







def generate_migration_overview(graph, output):


    logger.info(

        "[GraphGenerator v5] Selecting important mods..."

    )


    scores = {}


    for node in graph.nodes:


        scores[node] = (

            graph.degree(node)

            +

            graph.nodes[node].get(
                "score",
                0
            )

        )



    important = sorted(

        scores,

        key=scores.get,

        reverse=True

    )[:50]



    overview = graph.subgraph(
        important
    )



    draw_graph(

        overview,

        output,

        labels=True,

        title="UltraTech Migration Overview"

    )






def main():

    print("==============================")

    print(
        " Mod Dependency Graph Generator v5"
    )

    print("==============================")



    try:


        (
            json_path,

            full_output,

            labeled_output,

            overview_output

        ) = find_paths()



        mods = load_database(
            json_path
        )


        graph = build_graph(
            mods
        )


        logger.info(

            f"[GraphGenerator v{VERSION}] Nodes: {len(graph.nodes)}"

        )


        logger.info(

            f"[GraphGenerator v{VERSION}] Connections: {len(graph.edges)}"

        )



        generate_full_graph(

            graph,

            full_output

        )


        generate_full_graph_labeled(

            graph,

            labeled_output

        )


        generate_migration_overview(

            graph,

            overview_output

        )



        logger.success(

            "[GraphGenerator v5] Graph generation complete"

        )



    except Exception as error:


        logger.exception(
            error
        )



if __name__ == "__main__":

    main()