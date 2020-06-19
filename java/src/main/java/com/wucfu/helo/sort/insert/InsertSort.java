package com.wucfu.helo.sort.insert;

import sun.security.util.Length;

/**
 * Created by wuchangfu on 2019/5/14.
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] data = new int[]{9,3,2,1,4,5,7,6,8,0};
        for (int i : data) {
            System.out.print(i);
        }
        System.out.println();
        insertSort(data,0, data.length-1);
        for (int i : data) {
            System.out.print(i);
        }
    }
    public static void insertSort(int[] data, int start, int end){
        int temp;
        int j;
       for (int i=1,length = data.length; i<length; i++){
           // 方式1
           /*int num = data[i];
           int j = i-1;
           while (j>=0 && data[j] > num ){
               j--;
           }
           if (j<0)
               j = 0;
           else j++;
           if (j <= i-1 && j>=0){
               for (int k = i; k > j; k--){
                   data[k] = data[k-1];
               }
               data[j] = num;
           }*/

           // 方式2
           /*temp = data[i];
           //如果前一位(已排序的数据)比当前数据要大，那么就进入循环比较[参考第二趟排序]
           while (i>=1 && data[i - 1] > temp) {
               //往后退一个位置，让当前数据与之前前位进行比较
               data[i] = data[i - 1];
               //不断往前，直到退出循环
               i--;
           }
           //退出了循环说明找到了合适的位置了，将当前数据插入合适的位置中
           data[i] = temp;*/

            // 方式3
           /*temp = data[i];
            j = i;
           //如果前一位(已排序的数据)比当前数据要大，那么就进入循环比较[参考第二趟排序]
           while (j>=1 && data[j - 1] > temp) {

               //往后退一个位置，让当前数据与之前前位进行比较
               data[j] = data[j - 1];

               //不断往前，直到退出循环
               j--;
           }
           //退出了循环说明找到了合适的位置了，将当前数据插入合适的位置中
           data[j] = temp;*/

           // 方式4
           /*for(int index = 1; index<length; index++){//外层向右的index，即作为比较对象的数据的index
                temp = data[index];//用作比较的数据
               int leftindex = index-1;
               while(leftindex>=0 && data[leftindex]>temp){//当比到最左边或者遇到比temp小的数据时，结束循环
                   data[leftindex+1] = data[leftindex];
                   leftindex--;
               }
               data[leftindex+1] = temp;//把temp放到空位上
           }*/


        }// for-end
    }
}
