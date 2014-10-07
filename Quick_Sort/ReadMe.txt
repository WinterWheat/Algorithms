Quick Sort algorithm realized using C language.
The pivot is chosen randomly within the range.

QUICKSORT(A,p,r)

if p<r
    then q = Partion(A,p,r)
        QUICKSORT(A,p,q-1)
	QUICKSORT(A,q+1£¬r)

PRITIOIN(A,p,r)

pivot = rand(p,r)
exchange A[pivot] A[r]
x = A[r]
i = p - 1
for j = p to r-1
    if A[j] <= x
	then i = i+1
	exchange A[j] A[i]
exchange A[i+1] A[r]
return i+1