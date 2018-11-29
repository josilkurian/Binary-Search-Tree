import java.util.Scanner;

/**
 *
 * @author josilkm
 */

class Node{
    public Node leftNode, rightNode, parentNode;
    public int key, level;
    
    Node(int key){
        this.key = key;
        leftNode = rightNode = parentNode = null;
    }
    
    public int getKey(){
        return this.key;
    }
}
public class BST {
    static Node rootNode = null;
    static boolean nodeFoundFlag, displayFlag;
    static int level = 0, deletionLevel=0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BST bst = new BST();
        
        int choice = 1;
        while(choice != 0)
        {
            System.out.println("\nBinary Search Tree Options:");
            System.out.println("1. Insert\n2. Delete\n3. Search\n4. Min key value\n5. Max key value\n6. Successor\n7. Predecessor\n8. Traversal\n9. Delete all nodes\n0. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Enter key to insert:");
                    int keyToInsert = scanner.nextInt();
                    level = 0;
                    bst.insertNode(bst.rootNode, keyToInsert);
                    break;
                case 2:
                    displayFlag = false;
                    System.out.println("Enter key to delete:");
                    int keyToDelete = scanner.nextInt();
                    nodeFoundFlag = false;
                    level = 0;
                    deletionLevel = 0;
                    bst.searchNode(rootNode, keyToDelete);
                    if(nodeFoundFlag == true){
                        level = 0;
                        bst.deleteNode(rootNode, keyToDelete);
                    }
                    break;
                case 3:
                    displayFlag = true;
                    System.out.println("Enter key to Search:");
                    int keyToSearch = scanner.nextInt();
                    level = 0;
                    bst.searchNode(rootNode, keyToSearch);
                    break;
                case 4:
                    level = 0;
                    bst.findMinKeyValue();
                    break;
                case 5:
                    level = 0;
                    bst.findMaxKeyValue();
                    break;
                case 6:
                    displayFlag = false;
                    level = 0;
                    System.out.println("Enter key to find successor:");
                    int keyToFindSuccessor = scanner.nextInt();
                    nodeFoundFlag = false;
                    bst.searchNode(rootNode, keyToFindSuccessor);
                    if(nodeFoundFlag == true){
                        bst.findSuccessorKeyValue(rootNode, keyToFindSuccessor);
                    }
                    break;
                case 7:
                    displayFlag = false;
                    level = 0;
                    System.out.println("Enter key to find predecessor:");
                    int keyToFindpredecessor = scanner.nextInt();
                    nodeFoundFlag = false;
                    bst.searchNode(rootNode, keyToFindpredecessor);
                    if(nodeFoundFlag == true){
                        bst.findPredecessorKeyValue(rootNode, keyToFindpredecessor);
                    }
                    break;
                case 8:
                    if(null != rootNode){
                        bst.doTraversal();
                    }
                    else{
                        System.out.println("Tree is empty !");
                    }
                    break;
                case 9:
                    if(null != rootNode){
                        rootNode = null;
                        System.out.println("Tree deleted !");
                    }
                    else{
                        System.out.println("Tree is empty !");
                    }
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice !");
                    break;
            }
        }
        
        
    }
    
    void insertNode(Node node,int key){
        Node currentNode = node;
        if(null == rootNode){
            rootNode = new Node(key);
            rootNode.level = 0;
            System.out.println("Node inserted at depth:0\n");
        }
        else{
            level++;
            if(key <= currentNode.key){
                if(null == currentNode.leftNode){
                    currentNode.leftNode = new Node(key);
                    currentNode.leftNode.level = currentNode.level+1;
                    currentNode.leftNode.parentNode = currentNode;
                    System.out.println("Node inserted at depth: "+level+"\n");
                }
                else{
                    insertNode(currentNode.leftNode, key);
                }
            }
            else{
                if(null == currentNode.rightNode){
                    currentNode.rightNode = new Node(key);
                    currentNode.rightNode.level = currentNode.level+1;
                    currentNode.rightNode.parentNode = currentNode;
                    System.out.println("Node inserted at depth: "+level+"\n");
                }
                else{
                    insertNode(currentNode.rightNode, key);
                }
            }
        }
    }
    
    void deleteNode(Node node,int key){
        if(key < node.key){
            deleteNode(node.leftNode, key);
        }
        else if(key > node.key){
            deleteNode(node.rightNode, key);
        }
        else if(node.key == key){
            if(null == node.leftNode && null == node.rightNode){
                if(node == rootNode){
                    rootNode = null;
                }
                else if(isLeftChild(node)){
                    node.parentNode.leftNode = null;
                }
                else{
                    node.parentNode.rightNode = null;
                }
                node = null;
                System.out.println("Deleted node with key: "+key+" from level:"+deletionLevel);
            }
            else if(null != node.leftNode && null == node.rightNode){
                if(node == rootNode){
                    rootNode = node.leftNode;
                }
                else if(isLeftChild(node)){
                    node.parentNode.leftNode = node.leftNode;
                }
                else{
                    node.parentNode.rightNode = node.leftNode;
                }
                node.leftNode.parentNode = node.parentNode;
                node = null;
                System.out.println("Deleted node with key: "+key+" from level:"+deletionLevel);
            }
            else if(null == node.leftNode && null != node.rightNode){
                if(node == rootNode){
                    rootNode = node.rightNode;
                }
                else if(isLeftChild(node)){
                    node.parentNode.leftNode = node.rightNode;
                }
                else{
                    node.parentNode.rightNode = node.rightNode;
                }
                node.rightNode.parentNode = node.parentNode;
                node = null;
                System.out.println("Deleted node with key: "+key+" from level:"+deletionLevel);
            }
            else{
                Node successor = findSuccessor(node);
                if (node == rootNode) {
                    if (isRightChildSuccessor(node, successor)) {
                        successor.parentNode = null;
                    } else if (null != node.rightNode) {
                        successor.rightNode = node.rightNode;
                        node.rightNode.parentNode = successor;
                    }
                    if (null != node.leftNode) {
                        successor.leftNode = node.leftNode;
                        node.leftNode.parentNode = successor;
                    }
                    rootNode = successor;
                    node = null;
                    System.out.println("Deleted node with key: " + key+" from level:"+deletionLevel);
                }
                else {
                    if (isLeftChild(node)) {
                        node.parentNode.leftNode = successor;
                    } else {
                        node.parentNode.rightNode = successor;
                    }
                    if (isRightChildSuccessor(node, successor)) {

                    } else if (null != node.rightNode) {
                        successor.rightNode = node.rightNode;
                        successor.parentNode.leftNode = null;
                        node.rightNode.parentNode = successor;
                    }
                    if (null != node.leftNode) {
                        successor.leftNode = node.leftNode;
                        node.leftNode.parentNode = successor;
                    }
                    successor.parentNode = node.parentNode;
                    node = null;
                    System.out.println("Deleted node with key: " + key+" from level:"+deletionLevel);
                }
            }
        }
    }
    
    boolean isLeftChild(Node node){
        return node.parentNode.leftNode == node;
    }
    
    boolean isRightChildSuccessor(Node node, Node successor){
        return node.rightNode == successor;
    }
    
    Node findSuccessor(Node node) { 
        if (node.rightNode != null) { 
            return findMinNode(node.rightNode); 
        } 
        Node parent = node.parentNode; 
        while (parent != null && node == parent.rightNode) { 
            node = parent; 
            parent = parent.parentNode;
        } 
        return parent; 
    } 
    
    Node findRightMostNode(Node node){
        if(null != node.rightNode){
            
            findRightMostNode(node.rightNode);
        }
        return node;
    }
    
    Node findMaxNode(Node node){
        while (node.rightNode != null) { 
            node = node.rightNode; 
            level++;
        } 
        return node; 
    }
    
    Node findMinNode(Node node) { 
        while (node.leftNode != null) { 
            node = node.leftNode; 
            level++;
        } 
        return node; 
    } 
    
    Node findLeftMostNode(Node node){
        if(null != node.leftNode){
            findRightMostNode(node.leftNode);
        }
        return node;
    }
    
    void findMinKeyValue(){
        if(null == rootNode){
            System.out.println("Tree is empty !");
        }
        else{
            System.out.println("Minimum key value: "+findMinNode(rootNode).key+" at depth: "+level);
        }
    }
    
    void findMaxKeyValue(){
        if(null == rootNode){
            System.out.println("Tree is empty !");
        }
        else{
            System.out.println("Maximum key value: "+findMaxNode(rootNode).key+" at depth: "+level);
        }
    }
    
    void findSuccessorKeyValue(Node node, int key){
        if(key < node.key){
            findSuccessorKeyValue(node.leftNode, key);
        }
        else if(key > node.key){
            findSuccessorKeyValue(node.rightNode, key);
        }
        else {
            if (null == rootNode) {
                System.out.println("Tree is empty !");
            } 
            /*
            else if (null == rootNode.rightNode) {
                System.out.println("There is no successor for this node");
            } 
            */
            else {
                Node successor = findSuccessor(node);
                if(null == successor){
                    System.out.println("There is no successor for this node");
                }
                else{
                    displayFlag = false;
                    level = 0;
                    searchNode(rootNode, successor.key);
                    System.out.println("Successor of "+key+" is "+successor.key+" at depth: "+level);
                }
            }
        }
       
    }
    
    Node findPredecessor(Node node){
        if(null != node.leftNode){
            return findMaxNode(node.leftNode);
        }
        else {
            Node parent = node.parentNode;
            while (parent != null && node == parent.leftNode) {
                node = parent;
                parent = parent.parentNode;
            }
            return parent;
        }
    }
    
    void findPredecessorKeyValue(Node node, int key){
        if(key < node.key){
            findPredecessorKeyValue(node.leftNode, key);
        }
        else if(key > node.key){
            findPredecessorKeyValue(node.rightNode, key);
        }
        else {
            Node predecessor = findPredecessor(node);
            if (null == predecessor) {
                System.out.println("There is no predecessor for this node");
            } else {
                displayFlag = false;
                level = 0;
                searchNode(rootNode, predecessor.key);
                System.out.println("Predecessor of " + key + " is " + predecessor.key + " at depth: " + level);
            }
        }
    }
    
    void searchNode(Node node,int key){
        Node currentNode = node;
        if(null == rootNode){
            System.out.println("Node with key "+key+" not found !\n");
        }
        else{
            if(key == currentNode.key){
                if(displayFlag == true){
                    System.out.println("Node with key "+key+" found at depth: "+level+"\n");
                }
                deletionLevel = level;
                nodeFoundFlag = true;
            }
            else if(key <= currentNode.key){
                if(null != currentNode.leftNode){
                    level++;
                    searchNode(currentNode.leftNode, key);
                }
                else{
                    System.out.println("Node with key "+key+" not found !\n");
                }
            }
            else{
                if(null != currentNode.rightNode){
                    level++;
                    searchNode(currentNode.rightNode, key);
                }
                else{
                    System.out.println("Node with key "+key+" not found !\n");
                }
            }
        }
    }
    
    void doTraversal(){
        System.out.print("Preorder :");
        printPreorder(rootNode);
        System.out.print("\nInorder :");
        printInorder(rootNode);
        System.out.print("\nPostorder :");
        printPostorder(rootNode);
        System.out.println("\n");
    }
    
    void printPostorder(Node node) 
    { 
        if (node == null) 
            return; 
        printPostorder(node.leftNode); 
        printPostorder(node.rightNode); 
        System.out.print(node.key + " "); 
    } 
  
    void printInorder(Node node) 
    { 
        if (node == null) 
            return; 
        printInorder(node.leftNode); 
        System.out.print(node.key + " "); 
        printInorder(node.rightNode); 
    } 
  
    void printPreorder(Node node) 
    { 
        if (node == null) 
            return; 
        System.out.print(node.key + " "); 
        printPreorder(node.leftNode); 
        printPreorder(node.rightNode); 
    } 
    
}
