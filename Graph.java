package Lab6;

import java.io.*;
import java.util.*;

public class Graph
{
    public int noOfVertices = - 1;

    // Adjacency list representing the graph.
    public int[][] edges = null;
    public int[] times = null;


    // Creates a new empty graph.
    public Graph() { }

    public Graph(BufferedReader in)
    {
        int[] graphSize = readNextLine(in);
        noOfVertices = graphSize[0];

        edges = new int[noOfVertices][];
        times = new int[noOfVertices];

        for (int i = 0; i < noOfVertices; i++)
        {
            int[] vertInfo = readNextLine(in);

            edges[i] = new int[vertInfo.length - 1];
            times[i] = vertInfo[0];

            for (int j = 0; j < edges[i].length; j++)
            {
                edges[i][j] = vertInfo[j + 1];
            }
        }
    }

    private int[] readNextLine(BufferedReader in)
    {
        String line = null;

        try
        {
            line = in.readLine();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        String[] strNumbers = line.split("\\s");

        int[] numbers = new int[strNumbers.length];

        for (int i = 0; i < numbers.length; i++)
        {
            numbers[i] = Integer.parseInt(strNumbers[i]);
        }

        return numbers;
    }

    // ---------------------

    public int[][] dfs(int startId)
    {
        // Output data
        int[] parIds = new int[noOfVertices];

        int[] preOrder = new int[noOfVertices];
        int[] postOrder = new int[noOfVertices];

        int preCount = 0;
        int postCount = 0;


        // Helpers to compute DFS
        int[] neighIndex = new int[noOfVertices];
        int[] stack = new int[noOfVertices];
        int stackSize = 0;

        boolean[] visited = new boolean[noOfVertices];

        for (int vId = 0; vId < noOfVertices; vId++)
        {
            parIds[vId] = -1;

            preOrder[vId] = -1;
            postOrder[vId] = -1;

            visited[vId] = false;
            neighIndex[vId] = 0;
        }

        // Push
        stack[stackSize] = startId;
        stackSize++;

        while (stackSize > 0)
        {
            int vId = stack[stackSize - 1];
            int nInd = neighIndex[vId];

            if (nInd == 0)
            {
                visited[vId] = true;

                // *** Pre-order for vId ***
                preOrder[preCount] = vId;
                preCount++;
            }

            if (nInd < edges[vId].length)
            {
                int neighId = edges[vId][nInd];

                if (!visited[neighId])
                {
                    // Push
                    stack[stackSize] = neighId;
                    stackSize++;

                    parIds[neighId] = vId;
                }

                neighIndex[vId]++;
            }
            else
            {
                // All neighbours checked, backtrack.
                stackSize--; // Pop;

                // *** Post-order for vId ***
                postOrder[postCount] =  vId;
                postCount++;
            }
        }

        return new int[][]
        {
            parIds,
            preOrder,
            postOrder
        };
    }

    // ---------------------

    public int[][] bfs(int startId)
    {
        // Output data
        // 0: distances from start vertex
        // 1: BFS-order
        // 2: parent-IDs
        int[][] bfsResult = new int[3][noOfVertices];

        int[] distances = bfsResult[0];
        int[] q = bfsResult[1];
        int[] parents = bfsResult[2];

        for (int i = 0; i < noOfVertices; i++)
        {
            distances[i] = Integer.MAX_VALUE;
            q[i] = -1;
            parents[i] = -1;
        }


        // Set start vertex
        distances[startId] = 0;
        q[0] = startId;
        int qSize = 1;

        for (int qInd = 0; qInd < qSize; qInd++)
        {
            int vInd = q[qInd];
            int nDis = distances[vInd] + 1;

            for (int nInd = 0; nInd < edges[vInd].length; nInd++)
            {
                int uInd = edges[vInd][nInd];

                if (nDis < distances[uInd])
                {
                    distances[uInd] = nDis;
                    parents[uInd] = vInd;

                    q[qSize] = uInd;
                    qSize++;
                }
            }
        }

        return bfsResult;
    }
}
