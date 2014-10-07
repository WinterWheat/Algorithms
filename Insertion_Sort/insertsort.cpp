
#include "insertsort.h"

int InsertSort(int *arr, int arrLen)
{
    if(arr == NULL)
    {
        return -1;
    }
    int i, j;
    int key;
    for(j = 1; j<arrLen; j++)
    {
        key = arr[j];
        i = j - 1;
        while(i >= 0 && arr[i] > key)
        {
            arr[i+1] = arr[i];
            i = i - 1;
        }
        arr[i+1] = key;
    }
    return 0;
}
