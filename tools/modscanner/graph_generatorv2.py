import os
import json
import networkx as nx
import matplotlib.pyplot as plt


VERSION = "0.2"


def main():

    print("==============================")
    print(" Mod Dependency Graph Generator")
    print("==============================")
    print()


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


    json_file = os.path.join(
        docs,
        "mod_graph.json"
    )


    output = os.path.join(
        docs,
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



    node_colors = []



    for mod in mods:


        name = mod.get(
            "name",
            "Unknown"
        )


        graph.add_node(
            name,
            status=mod.get(
                "migration_status",
                "Unknown"
            )
        )



        for dep in mod.get(
            "dependencies",
            []
        ):


            graph.add_edge(
                dep,
                name
            )



    print(
        f"Nodes: {len(graph.nodes)}"
    )

    print(
        f"Edges: {len(graph.edges)}"
    )



    for node in graph.nodes:


        status = graph.nodes[node].get(
            "status",
            "Unknown"
        )


        if status == "Hard":

            node_colors.append(
                "red"
            )


        elif status == "Needs investigation":

            node_colors.append(
                "orange"
            )


        elif status == "Normal":

            node_colors.append(
                "green"
            )


        else:

            node_colors.append(
                "gray"
            )



    if len(graph.nodes) == 0:

        print(
            "Graph is empty"
        )

        return



    plt.figure(
        figsize=(24,18)
    )



    position = nx.spring_layout(
        graph,
        k=1,
        iterations=150
    )



    nx.draw_networkx_nodes(
        graph,
        position,
        node_color=node_colors,
        node_size=1800,
        alpha=0.8
    )



    nx.draw_networkx_edges(
        graph,
        position,
        arrows=True,
        alpha=0.5
    )



    nx.draw_networkx_labels(
        graph,
        position,
        font_size=8
    )



    plt.title(
        "UltraTech Migration Difficulty Graph"
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