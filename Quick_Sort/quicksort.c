#include "quicksort.h"

int QuickSort(int *arr, int p, int r)
{
    int q;
    if(arr == NULL)
    {
        return -1;
    }
    if(p < r)
    {
        q = Partition(arr, p, r);
        QuickSort(arr, p, q-1);
        QuickSort(arr, q+1, r);
    }
    return 0;
}

int Partition(int *arr, int p, int r)
{
    if(arr == NULL)
    {
        return -1;
    }
    int i, j;
    int sentry;
    int tmp;
    int randn;
    /*generate a random number between p and r*/
    randn = rand()%(r - p)+p;
    tmp = arr[randn];
    arr[randn] = arr[r];
    arr[r] = tmp;
    sentry = arr[r];
    i = p-1;
    for(j = p; j < r; j++)
    {
        if(arr[j] < sentry)
        {
            i = i+1;
            tmp = arr[j];
            arr[j] = arr[i];
            arr[i] = tmp;
        }
    }
    i = i+1;
    tmp = arr[r];
    arr[r] = arr[i];
    arr[i] = tmp;
    return i;
}
