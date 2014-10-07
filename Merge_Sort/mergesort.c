#include "mergesort.h"

int MergeSort(int *arr, int p, int r)
{
    int q;
    if(arr == NULL)
    {
        return -1;
    }
    if(p < r)
    {
        q = (p+r)/2;
        MergeSort(arr, p, q);
        MergeSort(arr, q+1, r);
        Merge(arr, p, q, r);
    }
    return 0;
}

int Merge(int *arr, int p, int q, int r)
{
    if(arr == NULL)
    {
        return -1;
    }
    int i,j,k;
    int L[q-p+2];
    int R[r-q+1];
    for(i = p; i <= q; i++)
    {
        L[i-p] = arr[i];
    }
    L[q-p+1] = 100000000;
    for(i = q+1; i <= r; i++)
    {
        R[i-q-1] = arr[i];
    }
    R[r-q] = 100000000;
    for(j = 0, k = 0, i = p; i <= r; i++)
    {
        if(L[k] < R[j])
        {
            arr[i] = L[k];
            k = k+1;
        }
        else
        {
            arr[i] = R[j];
            j = j+1;
        }
    }
    return 0;
}
