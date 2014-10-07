#include <stdio.h>
#include <stdlib.h>
#include "mergesort.h"

int main()
{
    int i;
    int arr[10] = {2, 3, 4, 7, 8, 1, 9, 0, 5, 6};
    printf("before sort:\n");
    for(i = 0;i < 10; i++)
    {
        printf("%d ",arr[i]);
    }
    MergeSort(arr, 0, 9);
    printf("\nafter sort:\n");
    for(i = 0;i < 10; i++)
    {
        printf("%d ",arr[i]);
    }
    return 0;
}
