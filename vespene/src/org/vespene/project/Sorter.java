package org.vespene.project;

/*
 * thanks to Leora http://snippets.dzone.com/posts/show/7353
 */

import java.util.LinkedList;

public class Sorter 
{
    private static <T extends Comparable<T> > LinkedList<T> merge(LinkedList<T>
                                                list1, LinkedList<T> list2)
   {
      LinkedList<T> list = new LinkedList<T>();
      while(!list1.isEmpty() && !list2.isEmpty())
      {
         if(list1.getFirst().compareTo(list2.getFirst()) < 0)
         {
            list.addLast(list1.removeFirst());
         }
         else 
         {
            list.addLast(list2.removeFirst());
         }   
      }
      list.addAll(list1);
      list.addAll(list2);
      return list;
   }
   
   public static <T extends Comparable<T> > LinkedList<T> mergeSort(LinkedList<T> list)
   {
      LinkedList<T> leftList = new LinkedList<T>();
      LinkedList<T> rightList = new LinkedList<T>();
      if(list.size() <= 1)
      {
         return list;
      }
      else
      {
         int middle = list.size() / 2;
         split(list, leftList, rightList, middle);
         leftList = mergeSort(leftList);
         rightList = mergeSort(rightList);
         list = merge(leftList, rightList);
         System.out.println("list size after merge: " + list.size());
         return list;
      }
   }
   
   private static <T extends Comparable<T> > void split(LinkedList<T> list,
                               LinkedList<T> leftList, LinkedList<T> rightList,
                               int middle)
   {
      
      for(int i = 1; i <= middle; ++i)
      {
         leftList.addLast(list.removeFirst());
      }
      rightList.addAll(list);
   }
}

