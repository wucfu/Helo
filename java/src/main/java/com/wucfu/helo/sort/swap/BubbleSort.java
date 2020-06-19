package com.wucfu.helo.sort.swap;

/**
 *冒泡排序
 *
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] data = new int[]{9,3,2,1,4,5,7,6,8,0};
        for (int i : data) {
            System.out.print(i);
        }
        System.out.println();
        bubbleSort(data);
        for (int i : data) {
            System.out.print(i);
        }
    }

    public static void bubbleSort(int[] data){
        // 相邻比较
        /*for (int i = 0; i < data.length; i++){
            for (int j = 0; j < data.length-1-i; j++){
                if (data[j]>data[j+1]) {
                    int temp = data[j+1];
                    data[j+1] = data[j];
                    data[j] = temp;
                }
            }
        }*/
        // 与头比较
        for (int i = 0; i < data.length; i++){
            for (int j = i+1; j < data.length; j++){
                if (data[i]>data[j]) {
                    int temp = data[j];
                    data[j] = data[i];
                    data[i] = temp;
                }
            }
        }
        // 改善，使最优时间复杂度为O(n)
        for (int i = 0; i < data.length; i++){
            boolean isSwap = false;
            for (int j = i+1; j < data.length; j++){
                if (data[i]>data[j]) {
                    int temp = data[j];
                    data[j] = data[i];
                    data[i] = temp;
                    isSwap = true;
                }
            }
            if (!isSwap){
                return;
            }
        }
    }
}
