/**************************************************************************************************/
/*  Copyright (C)   USTC@2014-2015    pengjiade@gmail.com                                         */
/*                                                                                                */
/*  FILE NAME             :  main.c                                                               */
/*  PRINCIPAL AUTHOR      :  PengJiade                                                            */
/*  SUBSYSTEM NAME        :  quicksort                                                            */
/*  MODULE NAME           :  quicksort                                                            */
/*  LANGUAGE              :  C                                                                    */
/*  TARGET ENVIRONMENT    :  ANY                                                                  */
/*  DATE OF FIRST RELEASE :  2014/10/07                                                           */
/*  DESCRIPTION           :  merge sort algorithms                                                */
/**************************************************************************************************/
/*
 * Revision log:
 *
 * Created by PengJiade, 2014/10/07
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include "quicksort.h"

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