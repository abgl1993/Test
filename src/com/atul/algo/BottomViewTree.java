package com.atul.algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class Node{
	
	int data,col;
	Node left,right;
	Node(int data){
		this.data=data;
		left=right=null;
	}
	
}
public class BottomViewTree {

	static Node[] bottomViewArray;
	public static void main(String args[]){
		Node root=new Node(20);
		root.left=new Node(8);
		root.right=new Node(22);
		root.left.left=new Node(5);
		root.left.right=new Node(3);
		root.right.right=new Node(25);
		root.left.right.left=new Node(10);
		root.left.right.right=new Node(14);
		
		display(root);
		System.out.println();
		displaybottomview(root);
	}

	private static void displaybottomview(Node root) {
		
		List<Node>nodesQueue=new LinkedList<Node>();
		
		List<Node>bottomView=new ArrayList<Node>();
		nodesQueue.add(root);
		
		root.col=0;
		while(!nodesQueue.isEmpty()){
			Node node=nodesQueue.remove(0);
			if(node!=null&&node.left!=null){
				node.left.col=node.col-1;
				nodesQueue.add(node.left);
				
			}if(node!=null&&node.right!=null){
				node.right.col=node.col+1;
				nodesQueue.add(node.right);
			}if(node!=null&&(node.left==null&&node.right==null)){ 
				bottomView.add(node);
			}
			
		}
		
		Iterator iterator=bottomView.iterator();
		
	   bottomViewArray=new Node[bottomView.size()];
	   int i=0;
	  while(iterator.hasNext()){
		  bottomViewArray[i]=(Node)iterator.next();
		  i++;
	  }
		
	    sort(bottomViewArray,0,bottomViewArray.length);
		for(i=0;i<bottomViewArray.length;i++){
			System.out.print(bottomViewArray[i].col+" ");
		}
		
	}

	private static void sort(Node[] bottomViewArray,int start,int end) {
		
		if(start<end){
			int partition=partition(bottomViewArray,start,end);
			sort(bottomViewArray,start,partition-1);
			sort(bottomViewArray,partition+1,end);
		}	
	
	}

	private static int partition(Node[] array,int start,int end) {
		
		int pivot=end-1;
		int i=start-1;
		int j=start;
		Node temp;
	
       for(j=start;j<end-1;j++){
			if(array[j].col<=array[pivot].col){
				i++;
				temp=array[i];
				array[i]=array[j];
				array[j]=temp;
			}
       }
		temp=array[i+1];
		array[i+1]=array[pivot];
		array[pivot]=temp;
		return i+1;
	}

	private static void display(Node root) {
		
		if(root==null){
			return;
		}
		System.out.print(root.data+" ");
		display(root.left);
		display(root.right);
		
	}
	
}

