import java.io.*;
import java.util.*;
//import javafx.util.Pair;
import java.nio.file.Files;
import java.nio.file.Paths;
public class my_player{
     //static Integer[][] board1;
     static int min;
    static int max;
     static int player;
    static int result;
    static  Integer[][] pboard;
    static  Integer[][] board;
    static ArrayList<Map.Entry<String,Integer[][]>>  states;
    static HashMap<String,Double[][]> hmap;
    public static void main(String [] args) throws IOException {
        //List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        result=0;
        hmap=new HashMap<String,Double[][]>();
        Scanner s = new Scanner(new File("input.txt"));
        //Scanner s1 = new Scanner(new File("winner.txt"));
        ArrayList<String> lines = new ArrayList<String>();
        board = new Integer[5][5];
        pboard = new Integer[5][5];
        while (s.hasNext()){
            lines.add(s.next());
        }
         player=Integer.parseInt(lines.get(0));
        //result=Integer.parseInt(s1.next());
        
        if(player==1){
            max=1;
            min=2;
        }
        else{
            max=2;
            min=1;
        }
        for(int k = 6; k<11;k++) {
			
			for(int l = 0; l< 5;l++){
				board[k-(6)][l] =Integer.parseInt(Character.toString((lines.get(k).toCharArray()[l])));
			}
            //System.out.println(origBoardState);
		}
        for(int k = 1; k<6;k++) {
			
			for(int l = 0; l< 5;l++){
				pboard[k-(1)][l] =Integer.parseInt(Character.toString((lines.get(k).toCharArray()[l])));
			}
            //System.out.println(origBoardState);
		}
        //System.out.println(origBoardState[0][3]);
        FileWriter writer = new FileWriter("output.txt");
        if(scoreStart(board)){
            writer.write("2,2");
        }
        else{
         HashMap<String,String> score1= alphabetaMax(2,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,board);
       
        if(score1.containsKey("move")){
            
           
		writer.write((String)score1.get("move"));
        }
        else
           writer.write("PASS"); 
        
       // System.out.println((String)score1.get("move"));
       //System.out.println((Double)score1.get("score"));
        }
        
        writer.close();
        s.close();
        
    }
    
    public  static Double score(Integer[][] b){
        Double score=0.0;
        int c=0,l=0;
         Double score1=0.0;
        Double score2=0.0;
         int m=0,n=0;
         int[] res= new int[2];
         Integer[][] tempB=new Integer[5][5];
                for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                tempB[x][y]=b[x][y];
            }
                }
                  // int m=find_deadm(tempB,3-player);
       // int n=find_deadm(tempB,player);
        res=find_deadm(tempB,3-player);
         m=res[0];
         n=res[1];
        //System.out.println("1:"+Arrays.deepToString(tempB));
                  tempB=find_deadN(tempB);
        //System.out.println("2:"+Arrays.deepToString(tempB));
                   // tempB=find_dead(tempB,player);
                   // tempB=find_dead(tempB,1);
       // System.out.println("3:"+Arrays.deepToString(tempB));
        
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(tempB[i][j]==player){
                   // if(player==2)
                    score+=libertyN(i,j,tempB);
                    score++;
                    
            }
                if(tempB[i][j]==3-player){
                    if(true)
                    score1+=(libertyN(i,j,tempB));
                    else{
                       score1+=(libertyN1(i,j,tempB)); 
                    /* if(i==0)
                        l++;
                    if(j==0)
                        l++;
                    if(i==4)
                        l++;
                    if(j==4)
                        l++;
                        score1+=l;*/
                    }
                   
                    score2++;
            }
        }
    }
       
        if(n>0)
           score-=2000.0*n;
        if(m>0)
            score+=2000.0*m;
        //if(player==2)
           //return score-score1;
        return score-score2-score1;
        //return score*5-score2*10-score1*10;
       // return -score1;
        //return m+score-score1-score2-n;
        
    }
    
     public  static Boolean scoreStart(Integer[][] b){
        Double score=0.0;
        int c=0;
         Double score1=0.0;
        Double score2=0.0;
         int m=0,n=0;
         int[] res= new int[2];
         Integer[][] tempB=new Integer[5][5];
                for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                tempB[x][y]=b[x][y];
            }
                }
                  // int m=find_deadm(tempB,3-player);
       // int n=find_deadm(tempB,player);
         
       // System.out.println("1:"+Arrays.deepToString(tempB));
                  tempB=find_deadN(tempB);
       // System.out.println("2:"+Arrays.deepToString(tempB));
                   // tempB=find_dead(tempB,player);
                   // tempB=find_dead(tempB,1);
       // System.out.println("3:"+Arrays.deepToString(tempB));
        
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
               if(tempB[i][j]!=0)
                   return false;
        }
    }
       
           return true;
       // return -score1;
        //return m+score-score1-score2-n;
        
    }
    
    
     
     public static Integer[][] ally_dfs(int i, int j,Integer[][] b){
            Stack<String> stack=new Stack<String>();
            //List<Integer[][]> visited=new ArrayList<Integer[][]>();
         HashSet<String> visited=new HashSet<String>();
         int l=0;
             Integer[][] piece=new Integer[25][2];
         
         Integer[][] v1=new Integer[25][2];
             stack.push(i+"-"+j);
             while(!stack.empty()){
                 Integer[][] allies=new Integer[4][2];
                 //System.out.println(visited);
                 //System.out.println((Integer[][])stack.pop());
                 String[] temp=stack.pop().split("-");
                 //piece=stack.pop();
                  //System.out.println(temp[1]);
                 piece[0][0]=Integer.parseInt(temp[0]);
                 piece[0][1]=Integer.parseInt(temp[1]);
                 //visited.add(new Integer[][]{{piece[0][0],piece[0][1]}});
                 visited.add(piece[0][0]+"-"+piece[0][1]);
                 //System.out.println(visited);
                 l++;
                 //allies=ally_neigh(piece[0][0],piece[0][1],b);
                 int p1=piece[0][0];
                 int p2=piece[0][1];
                 if(p1>0 && b[p1][p2]==b[p1-1][p2]){
                     allies[0][0]=p1-1;
                     allies[0][1]=p2;
                 }
                 if(p1<4 && b[p1][p2]==b[p1+1][p2]){
                    // allies[0][0]=b[p1+1][p2];
                     allies[1][0]=p1+1;
                     allies[1][1]=p2;
                 }
                 if(p2>0 && b[p1][p2]==b[p1][p2-1]){
                    // allies[0][0]=b[p1][p2-1];
                     allies[2][0]=p1;
                     allies[2][1]=p2-1;
                 }
                 if(p2<4 && b[p1][p2]==b[p1][p2+1]){
                     //allies[0][0]=b[p1][p2+1];
                     allies[3][0]=p1;
                     allies[3][1]=p2+1;
                 }
                 //if(i>0 && b[piece[0][0]][piece[0][1]==b[i][
                 for(int a=0;a<allies.length;a++){
                     if(allies[a][0]!=null){
                     //System.out.println((allies[a][0]+"-"+allies[a][1]));
                          //System.out.println(visited.contains(allies[a][0]+"-"+allies[a][1]));
                     if (allies[a][0]!=null&&stack.search(allies[a][0]+"-"+allies[a][1])==-1 && !visited.contains(allies[a][0]+"-"+allies[a][1])){
                         stack.push(allies[a][0]+"-"+allies[a][1]);
                     }
                     }
                 }
             }
         Iterator<String> it=visited.iterator();
         int a=0;
         while(it.hasNext()){
             //System.out.println(it.next().split("-")[1]);
             String[] temp=it.next().split("-");
             v1[a][0]=Integer.parseInt(temp[0]);
             v1[a][1]=Integer.parseInt(temp[1]);
             a++;
             //System.out.println(v1[0][1]);
            // v1[a][1]=visited.get(a)[1];
         }
            return v1;
        }   
    
    
      public static Boolean liberty(int i, int j, Integer[][] b){
                        
           Integer[][] allym= new Integer[25][2];
           //System.out.println(i+" "+j);
          allym=ally_dfs(i,j,b);
         //System.out.println(allym);
          
           for(int k=0;k<allym.length;k++){
               if(allym[k][0]!=null){
                    Integer[][] neighs= new Integer[4][2];
              // neighs=get_neigh(allym[k][0],allym[k][1],b);
                   int p1=allym[k][0];
                   int p2=allym[k][1];
                   if(p1>0 ){
                     neighs[0][0]=p1-1;
                     neighs[0][1]=p2;
                 }
                 if(p1<4 ){
                    // allies[0][0]=b[p1+1][p2];
                     neighs[1][0]=p1+1;
                     neighs[1][1]=p2;
                 }
                 if(p2>0 ){
                    // allies[0][0]=b[p1][p2-1];
                     neighs[2][0]=p1;
                     neighs[2][1]=p2-1;
                 }
                 if(p2<4 ){
                     //allies[0][0]=b[p1][p2+1];
                     neighs[3][0]=p1;
                     neighs[3][1]=p2+1;
                 }
                   for(int l=0;l<neighs.length;l++){
                       if(neighs[l][0]!=null&&b[neighs[l][0]][neighs[l][1]]==0)
                           return true;
                           
                   
           }
                        
                    }
           }
                        return false;
                    }
  
    public static int libertyN(int i, int j, Integer[][] b){
                        
           Integer[][] allym= new Integer[25][2];
        int c=0;
           //System.out.println(i+" "+j);
          allym=ally_dfs(i,j,b);
         //System.out.println(allym);
        //  Integer[][] neighs= new Integer[4][2];
           for(int k=0;k<allym.length;k++){
               if(allym[k][0]!=null){
                    Integer[][] neighs= new Integer[4][2];
              // neighs=get_neigh(allym[k][0],allym[k][1],b);
                    int p1=allym[k][0];
                   int p2=allym[k][1];
                   if(p1>0 ){
                     neighs[0][0]=p1-1;
                     neighs[0][1]=p2;
                 }
                 if(p1<4 ){
                    // allies[0][0]=b[p1+1][p2];
                     neighs[1][0]=p1+1;
                     neighs[1][1]=p2;
                 }
                 if(p2>0 ){
                    // allies[0][0]=b[p1][p2-1];
                     neighs[2][0]=p1;
                     neighs[2][1]=p2-1;
                 }
                 if(p2<4 ){
                     //allies[0][0]=b[p1][p2+1];
                     neighs[3][0]=p1;
                     neighs[3][1]=p2+1;
                 }
                   
                   for(int l=0;l<neighs.length;l++){
                       if(neighs[l][0]!=null&&(b[neighs[l][0]][neighs[l][1]]==0))//||b[neighs[l][0]][neighs[l][1]]==b[i][j]))
                          c++;
                       
                           
                   
           }
                   
                        
                    }
           }
        /*
        if(i==b.length-1)
                           c++;
        if(j==b.length-1)
                           c++;
        if(i==0)
                           c++;
        if(j==0)
                           c++;*/
        
                        return c;
                    }
  
    
    public static int libertyN1(int i, int j, Integer[][] b){
                        
           Integer[][] allym= new Integer[25][2];
        int c=0,a=0;
           //System.out.println(i+" "+j);
          allym=ally_dfs(i,j,b);
         //System.out.println(allym);
        //  Integer[][] neighs= new Integer[4][2];
           for(int k=0;k<allym.length;k++){
               if(allym[k][0]!=null){
                    Integer[][] neighs= new Integer[4][2];
              // neighs=get_neigh(allym[k][0],allym[k][1],b);
                    int p1=allym[k][0];
                   int p2=allym[k][1];
                   if(p1>0 ){
                     neighs[0][0]=p1-1;
                     neighs[0][1]=p2;
                 }
                 if(p1<4 ){
                    // allies[0][0]=b[p1+1][p2];
                     neighs[1][0]=p1+1;
                     neighs[1][1]=p2;
                 }
                 if(p2>0 ){
                    // allies[0][0]=b[p1][p2-1];
                     neighs[2][0]=p1;
                     neighs[2][1]=p2-1;
                 }
                 if(p2<4 ){
                     //allies[0][0]=b[p1][p2+1];
                     neighs[3][0]=p1;
                     neighs[3][1]=p2+1;
                 }
                   for(int l=0;l<neighs.length;l++){
                       if(neighs[l][0]!=null&&(b[neighs[l][0]][neighs[l][1]]==0))//||b[neighs[l][0]][neighs[l][1]]==b[i][j]))
                          c++;
                       
                       
                           
                   
           }
                   a+=(4-c);
                       c=0;
                   
                        
                    }
           }
        /*
        if(i==b.length-1)
                           c++;
        if(j==b.length-1)
                           c++;
        if(i==0)
                           c++;
        if(j==0)
                           c++;*/
        
                        return a;
                    }
  
                            
     public static Integer[][] find_dead(Integer[][] b, Integer currP){
                            Integer[][] dead= new Integer[25][2];
         Integer[][] tempB=new Integer[5][5];
                for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                tempB[x][y]=b[x][y];
            }
                }
                            int m=0;
                            for(int i=0;i<5;i++){
                                for(int j=0;j<5;j++){
                                    if(b[i][j]==currP && !liberty(i,j,b)){
                                        dead[m][0]=i;
                                        dead[m][1]=j;
                                        m++;
                                        tempB[i][j]=0;
                                        
                                    }
                            }
                            
                            
                        }
                             //tempB[5][0]=m;
                            return tempB;
                        }
    
    public static int[] find_deadm(Integer[][] b, Integer currP){
                            Integer[][] dead= new Integer[25][2];
        int[] res= new int[2];
         Integer[][] tempB=new Integer[5][5];
                for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                tempB[x][y]=b[x][y];
            }
                }
                            int m=0,n=0;
                            for(int i=0;i<5;i++){
                                for(int j=0;j<5;j++){
                                    if(b[i][j]==currP && !liberty(i,j,b)){
                                        dead[m][0]=i;
                                        dead[m][1]=j;
                                        m++;
                                        tempB[i][j]=0;
                                        
                                    }
                                    if(b[i][j]==3-currP && !liberty(i,j,b)){
                                        dead[m][0]=i;
                                        dead[m][1]=j;
                                        n++;
                                        tempB[i][j]=0;
                                        
                                    }
                            }
                            
                            
                        }
                            // tempB[5][0]=m;
        res[0]=m;
        res[1]=n;
                            return res;
                        }
    
     public static Integer[][] find_deadN(Integer[][] b){
                            Integer[][] dead= new Integer[25][2];
         Integer[][] tempB=new Integer[5][5];
                for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                tempB[x][y]=b[x][y];
            }
                }
                            int m=0;
                            for(int i=0;i<5;i++){
                                for(int j=0;j<5;j++){
                                    if(b[i][j]==3-player && !liberty(i,j,b)){
                                        dead[m][0]=i;
                                        dead[m][1]=j;
                                        m++;
                                        tempB[i][j]=0;
                                        
                                    }
                            }
                            
                            
                        }
                             //tempB[5][0]=m;
                            return tempB;
                        }
    public static HashMap<String, Integer[][]> actions( Integer[][] currB, Integer currP, Integer[][] pB){
        HashMap<String, Integer[][]> moves= new HashMap<String, Integer[][]> ();
        
       //System.out.println(Arrays.deepToString(currB));
        //System.out.println("player:"+currP);  
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                Integer[][] tempB=new Integer[5][5];
                Integer[][] tempB1=new Integer[5][5];
                for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                tempB[x][y]=currB[x][y];
                  tempB1[x][y]=currB[x][y];
            }
                      }
               if(currB[i][j]==0){
                  // tempB=java.util.Arrays.stream(currB).map(el -> el.clone()).toArray($ -> currB.clone());
                   if(currP==3-player){
                   tempB1=find_dead(tempB1,3-currP);
                   
                   //System.out.println(Arrays.deepToString(tempB1));
                 if(Arrays.deepEquals(tempB1,pboard)){
                    // System.out.println(Arrays.deepToString((Integer[][])moves.get("2,3")));
                            System.out.println("KO1");
                             continue;
                            //return moves;
                        }
                   }
                tempB[i][j]=currP;
                tempB1[i][j]=currP;
                if(!liberty(i,j,tempB)){
                   //System.out.println("player:"+currP+" i:"+i+" j:"+j);    
                    tempB=find_dead(tempB,3-currP);
                    if(!liberty(i,j,tempB)){
                        
                       // System.out.println(i+"a1"+j);
                        continue;
                    }
                    else{
                        //System.out.println("player:"+currP+" i:"+i+" j:"+j);  
                        tempB1=find_dead(tempB1,3-currP);
                   
                   //System.out.println(Arrays.deepToString(tempB1));
                 if(Arrays.deepEquals(tempB1,pboard)){
                    // System.out.println(Arrays.deepToString((Integer[][])moves.get("2,3")));
                            System.out.println("KO21");
                             continue;
                            //return moves;
                        }
                          
                        moves.put(i+","+j,tempB);
                         //System.out.println("player:"+currP+" i:"+i+" j:"+j);  
                        // System.out.println(Arrays.deepToString(tempB));
                       // System.out.println(i+" a2"+j);
                    }
                }
                else{
                   // System.out.println("player:"+currP+" i:"+i+" j:"+j);  
                   // tempB=find_dead(tempB,3-currP);
                    //System.out.println(Arrays.deepToString(tempB));
                    //System.out.println(Arrays.deepToString(pboard));
                    tempB1=find_dead(tempB1,3-currP);
                   
                   //System.out.println(Arrays.deepToString(tempB1));
                 if(Arrays.deepEquals(tempB1,pboard)){
                    // System.out.println(Arrays.deepToString((Integer[][])moves.get("2,3")));
                            System.out.println("KO22");
                             continue;
                            //return moves;
                        }
                          
                moves.put(i+","+j,tempB);
                  //  System.out.println(i+"a1"+j);
                    //System.out.println(Arrays.deepToString((Integer[][])moves.get("4,4")));
                   // System.out.println("player:"+currP+" i:"+i+" j:"+j);  
                     //System.out.println(Arrays.deepToString(tempB));
                  //  System.out.println(i+"a3"+j);
                }
                
               
        }
            }
        }
        //System.out.println("p "+currP);
        //System.out.println(moves);
            return moves;
    }
    
  
    
    
    public static HashMap<String,String> alphabetaMax(int d, Double alpha, Double beta, Integer[][] board){
        if(d==0){
            HashMap<String,String> res= new HashMap<>();
            res.put("score",""+score(board));
            //System.out.println(score(b));
            return res;
        }
       // if(maxi){
            
            HashMap<String,Integer[][]> act= actions(board,max,pboard);
            
            HashMap<String,String> res=new HashMap<>();
            res.put("score",""+Double.NEGATIVE_INFINITY);
            for(Map.Entry map: act.entrySet()){
                //System.out.println("done1");
                //System.out.println("m1 "+map.getKey());
                // System.out.println(Arrays.deepToString((Integer[][])act.get("2,3")));
                HashMap<String,String> score1= alphabetaMin(d-1,alpha,beta,(Integer[][])map.getValue());
               
                //System.out.print(map.getKey());
                if(Double.parseDouble(res.get("score"))<Double.parseDouble(score1.get("score"))){
                     res.put("score",""+score1.get("score"));
                   // System.out.println("1: "+score1.get("score"));
                   // System.out.println("1: "+map.getKey());
                    res.put("move",""+map.getKey());
                }
                if(Double.parseDouble(res.get("score"))>alpha){
                    alpha=Double.parseDouble(res.get("score"));
                    //System.out.println("m1 "+map.getKey());
                }
               // System.out.println("m1"+map.getKey()+" al:"+alpha+" b:"+beta);
                if(beta<=alpha){
                    //System.out.println("m1"+map.getKey());
                    break;
                    
                }
            }
            return res;
       
       
        
    }
     public static HashMap<String,String> alphabetaMin(int d, Double alpha, Double beta, Integer[][] board){
        if(d==0){
            HashMap<String,String> res= new HashMap<>();
            res.put("score",""+score(board));
           // System.out.println(score(b));
            return res;
        }
        
           HashMap<String,Integer[][]> act= actions(board,min,pboard);
         int i=0;
             //System.out.println(Arrays.deepToString((Integer[][])board));
           // System.out.print("done11");
            HashMap<String,String> res= new HashMap<>();
            res.put("score",""+Double.POSITIVE_INFINITY);
            for(Map.Entry map: act.entrySet()){
                i++;
                //System.out.print("done14");
                //System.out.println("m2"+map.getKey());
                //System.out.println(Arrays.deepToString((Integer[][])map.getValue()));
                HashMap<String,String> score1= alphabetaMax(d-1,alpha,beta,(Integer[][])map.getValue());
                
                if(Double.parseDouble(res.get("score"))>Double.parseDouble(score1.get("score"))){
                     res.put("score",score1.get("score"));
                    res.put("move",""+map.getKey());
                    //System.out.println("2: "+score1.get("score"));
                    //System.out.println("2: "+map.getKey());
                }
                
                if(Double.parseDouble(res.get("score"))<beta){
                    beta=Double.parseDouble(res.get("score"));
                    //System.out.println("m2 "+map.getKey());
                }
               // System.out.println("m2"+map.getKey()+" al:"+alpha+" b:"+beta);
                if(beta<=alpha){
                    
                    break;
                }
            }
             if(i==0)
                 res.put("score",""+score(board));
            return res;
        
    }
    
}
