#include <iostream>
#include "insertsort.h"
using namespace std;

/*
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
*/

int main()
{
    int i;
    int array[10] = {1,3,5,6,8,9,7,0,2,4};
    cout<<"Before Sort:\n";
    for(i = 0; i < 10; i++)
    {
        cout<<" "<<array[i];
    }
    InsertSort(array, 10);
    cout<<"\nAfter Sort:\n";
    for(i = 0; i < 10; i++)
    {
        cout<<" "<<array[i] ;
    }
    return 0;
}
