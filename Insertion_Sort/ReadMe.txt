Insertion Sort algorithm in C language.

INSERTIONSORT(A,p,q);

i = p;
for j = p+1 to q
    key = A[j]
    i = j -1;
    while i>0 & a[i] > key
    	a[i+1] = a[i]
    a[i+1] = a[j] 