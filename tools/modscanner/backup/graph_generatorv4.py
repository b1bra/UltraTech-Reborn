import os
import json
import networkx as nx
import matplotlib.pyplot as plt


VERSION = "4"


def log(text):
    print(f"[GraphGenerator v{VERSION}] {text}")



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

    return (
        os.path.join(
            docs,
            "mod_graph.json"
        ),

        os.path.join(
            docs,
            "mod_graph.png"
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


    with open(
        path,
        "r",
        encoding="utf-8"
    ) as file:

        return json.load(file)




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
            )
        )



    # Добавление зависимостей

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
                    size=800
                )


            graph.add_edge(
                dependency,
                name
            )



    return graph





def generate(graph, output):


    if len(graph.nodes) == 0:

        raise Exception(
            "Graph is empty"
        )



    log(
        f"Nodes: {len(graph.nodes)}"
    )


    log(
        f"Connections: {len(graph.edges)}"
    )



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



    log(
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



    nx.draw_networkx_labels(
        graph,
        position,
        font_size=8
    )



    plt.title(
        "UltraTech Migration Dependency Graph"
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



def main():

    print("==============================")
    print(" Mod Dependency Graph Generator")
    print("==============================")
    print()


    try:


        json_path, output = find_paths()


        log(
            "Loading database..."
        )


        mods = load_database(
            json_path
        )


        graph = build_graph(
            mods
        )


        generate(
            graph,
            output
        )


        print()


        log(
            "=============================="
        )

        log(
            "Graph generation complete"
        )

        log(
            output
        )

        log(
            "=============================="
        )


    except Exception as error:


        print()

        log(
            "ERROR:"
        )

        print(
            error
        )


    finally:

        pass




if __name__ == "__main__":

    main()