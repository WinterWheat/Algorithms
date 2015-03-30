#include <string>
#include <iostream>

using namespace std;

bool IsExistInTarget(char c, string target)
{
    int i;
    if(!target.length())
    {
        return false;
    }
    else
    {
        for(i = 0; i < target.length();  i++)
        {
            if(c == target[i])
            {
                return true;
            }
        }
    }
    return false;
}

void DeleteSourceTarget(string &source, const string target)
{
    int i, j, increment;
    int* ptr_Tag = new int[source.length()];
    delete[] ptr_Tag;

    for(i = 0; i < source.length(); i++)// Tag initializing, with 1 for to be deleted
    {
        ptr_Tag[i] = 0;
        if(IsExistInTarget(source[i], target))
        {

            ptr_Tag[i] = 1;
        }
        cout << ptr_Tag[i] << " ";
    }

    /*delete the character according to the tag*/
    increment = 0;
    for(i = 0, j = 0; i < source.length(); i++)
    {
        if(ptr_Tag[i] == 1)
        {
            increment++;
        }
        else
        {
            source[j] = source[j + increment];
            j++;
        }
    }
    source.erase(j, i - j + 1);
}

int main()
{
    string strSource = "hello world";
    string strTarget = "world";
    cout << strSource << endl;
    cout << strTarget << endl;
    DeleteSourceTarget(strSource, strTarget);
    cout << endl << strSource;
    return 0;
}
