package com.wucfu.helo.sort.insert;

/**
 * @ClassName ShellSort
 * @Description: TODO
 * @Author wuchangfu
 * @Date 2019/9/2
 **/
public class ShellSort {
    public static void main(String[] args) {
        int[] data = new int[]{9,3,2,1,4,5,7,6,8,0};
        for (int i : data) {
            System.out.print(i);
        }
        System.out.println();
        shellSort(data);
        for (int i : data) {
            System.out.print(i);
        }
    }
    public static void shellSort(int[] arr){
        int number = arr.length / 2;
        int i;
        int j;
        int temp;
        while (number >= 1) {
            for (i = number; i < arr.length; i++) {
                temp = arr[i];
                j = i - number;
                //需要注意的是，这里array[j] > temp将会使数组从小到到排序。
                while (j >= 0 && arr[j] > temp) {
                    arr[j + number] = arr[j];
                    j = j - number;
                }
                arr[j + number] = temp;
            }
            number = number / 2;
        }
    }
}
