package Lab6;

import java.io.*;
import java.util.*;

public class Lab6
{


    // Problem: Determine the minimum maximum meeting time.

    private static int problem(Graph g)
    {
        //initialize variables, source nodes will be added to min heap, and then the stack
        int longestMeeting = Integer.MIN_VALUE;
        int topOfStack;
        int removedNode; // used to improve readability
        Stack<Integer> topologicalOrder = new Stack<>();
        int[] incomingEdges = new int[g.noOfVertices];
        MinHeap heap = new MinHeap(g.noOfVertices);
        
        //find initial sources
        for(int i = 0; i < g.edges.length; i++) {
            for(int j = 0; j < g.edges[i].length; j++) {
                incomingEdges[g.edges[i][j]]++;
            }
        }
        
        //add initial sources to heap
        for(int i = 0; i < incomingEdges.length; i++) {
            if(incomingEdges[i] == 0) {
                heap.add(g.times[i], i);
            }
        }

        //remove sources and process their neighbors. If neighbors are now sources, add them to heap
        while(heap.getSize() != 0) {
            removedNode = heap.removeMin()[1];
            topologicalOrder.push(removedNode);
            
            for(int i = 0; i < g.edges[removedNode].length; i++) {
                incomingEdges[g.edges[removedNode][i]]--;
                
                if(incomingEdges[g.edges[removedNode][i]] == 0) {
                    heap.add(g.times[g.edges[removedNode][i]], g.edges[removedNode][i]);
                }
            }
         }
        
        //find the longest meeting
        for(int i = 0; i < g.noOfVertices; i++) {
            topOfStack = g.times[topologicalOrder.pop()];
            if(topOfStack + i > longestMeeting) {
                longestMeeting = topOfStack + i;
            }
        }
        
        return longestMeeting;
    }

    private static final int LabNo = 6;

    //Create an array with all solutions, compare answers to solutions
    private static boolean testProblem(BufferedReader in)
    {
        Graph g = new Graph(in);

        int[] solution = readNextLine(in);
        int answer = problem(g);

        return answer == solution[0];
    }

    public static void main(String args[])
    {
        System.out.println("Meeting Scheduler " + LabNo);

        BufferedReader in;

        try
        {
            in = new BufferedReader(new FileReader("testsLab" + LabNo + ".txt"));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        int noOfTests = 0;

        try
        {
            noOfTests = Integer.parseInt(in.readLine());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("-- -- -- -- --");
        System.out.println(noOfTests + " test cases.");

        boolean passedAll = true;

        for (int i = 1; i <= noOfTests; i++)
        {
            readNextLine(in);

            boolean passed = false;
            boolean exce = false;

            try
            {
                passed = testProblem(in);
            }
            catch (Exception ex)
            {
                passed = false;
                exce = true;
            }

            if (!passed)
            {
                System.out.println("Test " + i + " failed!" + (exce ? " (Exception)" : ""));
                passedAll = false;
            }
        }

        if (passedAll)
        {
            System.out.println("All test passed.");
        }

    }

    private static int[] readNextLine(BufferedReader in)
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

        if(line == null || line.isEmpty()) return null;

        String[] strNumbers = line.split("\\s");

        int[] numbers = new int[strNumbers.length];

        for (int i = 0; i < numbers.length; i++)
        {
            numbers[i] = Integer.parseInt(strNumbers[i]);
        }

        return numbers;
    }

}
