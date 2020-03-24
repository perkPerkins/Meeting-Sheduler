package Lab6;

import java.io.*;
import java.util.*;

public class MinHeap
{
    // - - - - Private Variables - - - -

    private int[] keys = null;
    private int[] values = null;
    private int size = 0;


    // - - - - Constructors - - - -

    public MinHeap(int capacity)
    {
        keys = new int[capacity];
        values = new int[capacity];
    }

    public MinHeap(int[] keys, int[] values)
    {
        if (keys.length != values.length)
        {
            System.out.println("* * * Error: Keys and Values need to have the same size! * * *");
            return;
        }

        this.keys = keys;
        this.values = values;

        size = keys.length;

        for (int i = keys.length / 2 - 1; i >= 0; i--)
        {
            heapify(i);
        }
    }


    // - - - - Getter Functions - - - -

    public int getCapacity()
    {
        return keys.length;
    }

    public int getSize()
    {
        return size;
    }


    // - - - - Public Heap-Operations - - - -

    public void add(int key, int val)
    {
        if (getSize() >= getCapacity())
        {
            System.out.println("* * * Error: Heap is already full! * * *");
            return;
        }

        keys[size] = key;
        values[size] = val;

        size++;

        moveUp(size - 1);
    }

    public int getMinKey()
    {
        return keys[0];
    }

    public int getMinValue()
    {
        return values[0];
    }

    public int[] removeMin()
    {
        int[] min = new int[] { keys[0], values[0] };

        size--;

        keys[0] = keys[size];
        values[0] = values[size];

        heapify(0);

        return min;
    }


    // - - - - Private Heap-Operations - - - -

    private void heapify(int index)
    {
        while (true)
        {
            int l = left(index);
            int r = right(index);

            if (l >= getSize())
            {
                break;
            }

            int smallest = l;
            if (r < getSize() && keys[r] < keys[l])
            {
                smallest = r;
            }

            if (keys[smallest] >= keys[index])
            {
                break;
            }

            swapKeys(smallest, index);
            index = smallest;
        }
    }

    private void moveUp(int index)
    {
        while (index > 0)
        {
            int parInd = parent(index);

            if (keys[parInd] <= keys[index])
            {
                return;
            }

            swapKeys(parInd, index);
            index = parInd;
        }
    }


    // - - - - Helper Functions - - - -

    private int left(int index)
    {
        return 2 * index + 1;
    }

    private int right(int index)
    {
        return 2 * index + 2;
    }

    private int parent(int index)
    {
        return (index - 1) / 2;
    }

    private void swapKeys(int ind1, int ind2)
    {
        int tmpK = keys[ind1];
        int tmpV = values[ind1];

        keys[ind1] = keys[ind2];
        values[ind1] = values[ind2];

        keys[ind2] = tmpK;
        values[ind2] = tmpV;
    }

}
