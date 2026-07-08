import os
import json
import networkx as nx
import matplotlib.pyplot as plt


VERSION = "1"



def log(text):
    print(f"[GraphGenerator v{VERSION}] {text}")




def find_data():

    current = os.path.dirname(
        os.path.abspath(__file__)
    )


    root = os.path.dirname(
        os.path.dirname(current)
    )


    docs = os.path.join(
        root,
        "docs"
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

    if status == "Hard":
        return "red"

    elif status == "Normal":
        return "orange"

    elif status == "Easy":
        return "green"

    else:
        return "gray"





def get_size(mod):

    size = mod.get(
        "size_mb",
        1
    )


    files = mod.get(
        "files",
        100
    )


    value = (
        size * 200
        +
        files / 5
    )


    return max(
        500,
        min(
            value,
            6000
        )
    )





def main():


    print("==============================")
    print(" Mod Dependency Graph Generator")
    print("==============================")
    print()


    json_file, output = find_data()



    if not os.path.exists(json_file):

        log(
            "ERROR: mod_graph.json not found"
        )

        input(
            "Press Enter to exit..."
        )

        return




    log(
        "Loading mod database..."
    )


    with open(
        json_file,
        "r",
        encoding="utf-8"
    ) as file:

        mods = json.load(file)




    graph = nx.DiGraph()


    colors = []

    sizes = []



    log(
        "Creating graph..."
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
            status=mod.get(
                "difficulty",
                "Unknown"
            )
        )


        colors.append(
            get_color(
                mod.get(
                    "difficulty",
                    "Unknown"
                )
            )
        )


        sizes.append(
            get_size(
                mod
            )
        )



        for dependency in mod.get(
            "dependencies",
            []
        ):


            graph.add_edge(
                dependency,
                name
            )





    log(
        f"Nodes: {len(graph.nodes)}"
    )


    log(
        f"Connections: {len(graph.edges)}"
    )



    if len(graph.nodes) == 0:

        log(
            "Graph empty"
        )

        return




    log(
        "Generating layout..."
    )


    position = nx.spring_layout(
        graph,
        k=1,
        iterations=200
    )



    plt.figure(
        figsize=(28,20)
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
        alpha=0.4
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



    log(
        "Saving image..."
    )


    plt.savefig(
        output,
        dpi=300,
        bbox_inches="tight"
    )


    plt.close()



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


    input(
        "Press Enter to exit..."
    )





if __name__ == "__main__":
    main()