import os
import json
import networkx as nx
import matplotlib.pyplot as plt


def main():

    print("==============================")
    print(" Mod Dependency Graph")
    print("==============================")
    print()


    current = os.path.dirname(
        os.path.abspath(__file__)
    )


    root = os.path.dirname(
        os.path.dirname(current)
    )


    json_file = os.path.join(
        root,
        "docs",
        "mod_graph.json"
    )


    output = os.path.join(
        root,
        "docs",
        "mod_graph.png"
    )


    if not os.path.exists(json_file):

        print(
            "ERROR: mod_graph.json not found"
        )

        input(
            "Press Enter..."
        )

        return



    with open(
        json_file,
        "r",
        encoding="utf-8"
    ) as file:

        mods = json.load(file)



    graph = nx.DiGraph()



    for mod in mods:

        name = mod.get(
            "name",
            "Unknown"
        )


        graph.add_node(
            name
        )


        dependencies = mod.get(
            "dependencies",
            []
        )


        for dep in dependencies:

            if isinstance(dep, str):

                graph.add_edge(
                    dep,
                    name
                )



    print(
        f"Nodes: {len(graph.nodes)}"
    )

    print(
        f"Connections: {len(graph.edges)}"
    )


    if len(graph.nodes) == 0:

        print(
            "Graph is empty"
        )

        return



    plt.figure(
        figsize=(20, 15)
    )


    position = nx.spring_layout(
        graph,
        k=0.8,
        iterations=100
    )


    nx.draw(
        graph,
        position,
        with_labels=True,
        node_size=1500,
        font_size=8,
        arrows=True
    )


    plt.title(
        "UltraTech Mod Dependency Graph"
    )


    plt.savefig(
        output,
        dpi=300,
        bbox_inches="tight"
    )


    plt.close()


    print()

    print(
        "Graph saved:"
    )

    print(
        output
    )


    input(
        "Press Enter to close..."
    )



if __name__ == "__main__":
    main()