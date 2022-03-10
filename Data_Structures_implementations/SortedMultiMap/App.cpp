#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"

/*
 ADT SortedMultiMap–using  a  BST  with  linked representation  with
 dynamic  allocation.  In the BST(key, value) pairs are stored. If a key has
 multiple values, it appears in multiple pairs.
 */

int main()
{
    testAll();
	testAllExtended();

    std::cout<<"Finished SMM Tests!"<<std::endl;
//	system("pause");
}
