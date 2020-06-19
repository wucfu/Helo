package com.wucfu.helo.sort.swap;

/**
 * Created by wuchangfu on 2019/5/11.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] data = new int[]{9,3,2,1,4,5,7,6,8,0};
        for (int i : data) {
            System.out.print(i);
        }
        System.out.println();
        quickSort(data,0, data.length-1);
        for (int i : data) {
            System.out.print(i);
        }
    }
    public static void quickSort(int[] data, int start, int end){
        if (start >= end){
            return;
        }
        int temp = data[start];
        int leftIndex = start;
        int rightIndex = end;
        while (leftIndex != rightIndex){
            // 从右边开始找
            while (data[rightIndex] >= temp &&  rightIndex > leftIndex) {
                rightIndex--;
            }
            // 从左边开始找
            while (data[leftIndex] <= temp && rightIndex > leftIndex){
                leftIndex++;
            }
            // 交换
            if (rightIndex > leftIndex){
                int rightData = data[rightIndex];
                data[rightIndex] = data[leftIndex];
                data[leftIndex] = rightData;
            }
        }
        // 基准值归位
        data[start] = data[leftIndex];
        data[leftIndex] = temp;
        // 递归
        quickSort(data,start ,leftIndex - 1);
        quickSort(data, leftIndex + 1, end);
    }
}
