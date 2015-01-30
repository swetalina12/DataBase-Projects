import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import java.util.TreeMap;

public class MyDatabase 
{    //declared files
     static String idFile="id.ndx";
     static String lastNameFile="last_name.ndx";
     static String stateFile="state.ndx";
     static String databaseFile ="data.db";
     static String tempFile="temp.txt";
     //treemaps
     static Map<Integer, Long> idTree = new TreeMap<Integer, Long>();
     static Iterator<Map.Entry<Integer, Long>> idIterator = idTree.entrySet().iterator();
     static Map<String, ArrayList<Long>> lnameTree = new TreeMap <String, ArrayList<Long>>();
     static Map<String, ArrayList<Long>> stateTree = new TreeMap <String, ArrayList<Long>>();
     static long mainOffset=0;
     static int no_records=1;
     static int fileSize=0;
     static int lastID=0;
    public static void main(String[] args) 
    { 
        loadDatabase();// creating .db file and index files
        
        String input="Butt";
        select(input); //select functionality
        select (33);
        select ("SC");

       String record="'502','James','Butt','Benton, John B Jr','6649 N Blue Gum St','New Orleans','Orleans','LA','70116','504-621-8927','504-845-1427','jbutt@gmail.com','http://www.bentonjohnbjr.com'";
       insert(record);
       select(input); 
       
       count();//Calling count function
       
       String field="first_name";
       String newData="Games";
       System.out.println("\nModify new record for id 502 change "+field+" to "+newData);
       modify(502,field,newData); //modify functionality
       field="last_name";
       newData="Dass";
       System.out.println("\nModify new record id 502 change "+field+" to "+newData);
       modify(502,field,newData); //modify functionality
       
       input="Dass";
       select(input);
       
       delete(511); 
       delete(2);
       select(2); 
         count();
              
    }
  public static void loadDatabase(){
        try 
        {
            int iterator=0;
            String csvFile ="us-500.csv";
            String line;  
            //Reading File
            FileReader inputFile = new FileReader(csvFile);     
            BufferedReader in = new BufferedReader(inputFile);
            File f = new File(databaseFile); 
            if(!f.exists()){
                 //for writing file
             FileWriter fstream = new FileWriter(databaseFile);
             BufferedWriter  bw = new BufferedWriter(fstream);
            while ((line = in.readLine()) != null) 
            {
                if(iterator == 0) 
                {
                    iterator++;  
                    continue;
                }
            
                //split data
               String dataToWrite[] = line.split("\",\"|\",|,\""); 
              //append a delimiter and newline
              line+="*\n";
              //bw.write(line);
                writeToRandomAcessFile(databaseFile, line,mainOffset);
                no_records++;
              //creating tree map
                for(int i=0;i<13;i++)
                {  
                    switch(i)
                    {
                            case 0:
                                String temp = dataToWrite[i].replace ("\"", ""); 
                                int id=Integer.parseInt(temp);
                                idTree.put(id,mainOffset);
                                lastID=id;
                                break;
                            case 2:
                              String temp1 = dataToWrite[i].replace ("\"", "");
                              //temp1=temp1.toUpperCase();
                                if( lnameTree.containsKey(temp1)){
                                ArrayList<Long> lastnameList= lnameTree.get(temp1);
                                lastnameList.add(mainOffset);
                                lnameTree.put(temp1, lastnameList);
                                }
                                
                                else{ 
                                   ArrayList<Long> lastnameList = new ArrayList<Long>();
                                   lastnameList.add(mainOffset);
                                   lnameTree.put(dataToWrite[i], lastnameList);}
                                  break;
                            case 7:
                                 String temp2 = dataToWrite[i].replace ("\"", ""); 
                                 if( stateTree.containsKey(temp2)){
                                ArrayList<Long> stateList= stateTree.get(temp2);
                                stateList.add(mainOffset);
                                stateTree.put(temp2, stateList);
                                } 
                                else{ 
                                   ArrayList<Long> stateList = new ArrayList<Long>();
                                   stateList.add(mainOffset);
                                   stateTree.put(temp2, stateList);}
                                   break;
                            default: continue;
                    }     		
                } 
                
                 mainOffset+=line.length(); 
                 iterator++;
            }
         bw.close();
         
         //write id.ndx
        writeIndexFile(idTree);
         
          //write lastname.ndx file
        writelnameFile(lnameTree);

           //write state.ndx File
        writestateFile(stateTree);
            }
            
            else{
               readIndexFiles(); 

            }
            
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
      }
    
   private static int writeFile(String filePath,String indexData)
   {
       try
       {
       FileOutputStream writer = new FileOutputStream(filePath);
            FileWriter  outputFile = new FileWriter (filePath,true);     
            BufferedWriter  out = new BufferedWriter (outputFile);
            out.flush();
            out.write(indexData);
           // out.flush();
            out.close();
            outputFile.close();
       }catch(Exception ex)
       {
           return -1;
       }
       return -1;
        
    }
   
   private static void writeIndexFile( Map<Integer, Long> idTree){
         String idvalue=idTree.toString();
          String value = idvalue.replace ("=", " "); 
          value = value.replace (",", "\n"); 
          value =value.replace("{", "");
          value =value.replace("}", "");
          writeFile(idFile,value);   
        
   }
   
    private static char readFromRandomAcessFile(String filePath, long position, int size)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile(filePath, "r");
            file.seek(position);
            char r= (char)file.readByte();
            file.close();
            return r;
        }catch(Exception ex)
        {
            return 0;
        }
       
    }
    
    private static void writeToRandomAcessFile(String filePath, String data, long position)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(position);
            file.write(data.getBytes());
            file.close();
        }
        catch(Exception ex)
        {
            return;
        }
    }

    private static void writelnameFile(Map<String, ArrayList<Long>> lnameTree) {
         String lastnamevalue=lnameTree.toString();
          String lvalue=lastnamevalue.replace("=[", "=");
          lvalue=lvalue.replace("],", "\n");
          lvalue=lvalue.replace("\"", "");
          lvalue=lvalue.replace("{", "");
          lvalue=lvalue.replace("}", "");
          lvalue=lvalue.replace("]", "");
          lvalue=lvalue.replaceAll(" ", "");
          writeFile(lastNameFile,lvalue);
         
    }

    private static void writestateFile(Map<String, ArrayList<Long>> stateTree) {
         String statevalue=stateTree.toString();
          String stvalue=statevalue.replace("=[", "=");
          stvalue=stvalue.replace("],", "\n");
          stvalue=stvalue.replace("\"", "");
          stvalue=stvalue.replace("{", "");
          stvalue=stvalue.replace("}", "");
          stvalue=stvalue.replace("]", "");
          stvalue=stvalue.replaceAll(" ", "");
          writeFile(stateFile,stvalue);
          // System.out.print(stvalue);
    }   
    
    //for Int type index
    private static void select(int i) {
         System.out.println("\nPrinting data with id: "+i);
        if(idTree.get(i)!=null){
        long idoffset = idTree.get(i);
            while(true){
            char print=readFromRandomAcessFile(databaseFile,idoffset,1);
            if(print == '*')
            {break;}
            System.out.print(print);
            idoffset++;
           } 
            System.out.println();
          
        }
        else{
            
               System.out.println("Not a Valid key");
              }
    }

     //for String type index
    private static void select(String i) {
         System.out.println("Printing data with last name: "+i);
       if (lnameTree.get(i)!=null){     
       ArrayList<Long> lnameoffset =  lnameTree.get(i);
           for (int j=0;j<lnameoffset.size();j++){
           long read=lnameoffset.get(j);
            while(true){
            char print=readFromRandomAcessFile(databaseFile,read,1);
            if(print == '*')
            {break;}
            System.out.print(print);
            read++;
           } 
            System.out.println();
           }
           
       } 
       else if (stateTree.get(i)!=null){
           ArrayList<Long> stateoffset =  stateTree.get(i);
           for (int j=0;j<stateoffset.size();j++){
           long read=stateoffset.get(j);  
            while(true){
            char print=readFromRandomAcessFile(databaseFile,read,1);
            if(print == '*')
            {break;}
            System.out.print(print);
            read++;
           } 
            System.out.println();
           } 
       }
         else{
               System.out.println("Not a Valid key");
              }
    }

    private static void modify(int i, String field, String newData) {
    String[] fieldArray={"id","first_name","last_name","company_name","address","city","county","state","zip","phone1","phone2","email","web"};
    int index=0;
    boolean flag=true;
    String record="";
    if (idTree.get(i)!=null){
    long offset=idTree.get(i);
       while(true){
            char print=readFromRandomAcessFile(databaseFile,offset,1);
            if(print == '*')
            {break;}
            record+=print;
            //System.out.println(record);
            offset++;
           } 
        String dataToWrite[] = record.split("\",\"|\",|,\"");
        
       for(int k=0;k<13;k++){
           if(field== fieldArray[k])
               index=k;
       }
       
       
       String oldData=dataToWrite[index];
       oldData=oldData.replace ("\"", "");
       dataToWrite[index]=newData;
       if (newData.length()!= oldData.length()){
            System.out.println("System cannot handle this, the new data is not of same length as old data ");
       }
       else{
       StringBuilder builder = new StringBuilder();
       for(String s : dataToWrite) {     
       builder.append("\""+s+"\",");
      
        }
       if(index==0)
       record=builder.toString().substring(0,record.length());
       else
       record=builder.toString().substring(1,record.length()); //to remove the extra , introduced
       
       offset=idTree.get(i);
      
        if(index==0){
             if(!idTree.containsKey(Integer.parseInt(newData))){
            idTree.remove(Integer.parseInt(oldData));
            idTree.put(Integer.parseInt(newData), offset);
             writeIndexFile(idTree);
               }
             
           else{
           System.out.println("Unique Identifier already exists ");
           flag=false;
       }
       }
      
       
      if(flag){
       writeToRandomAcessFile(databaseFile,record,offset);}
       
       
      
       if (index==2)
       {   
          ArrayList<Long> lnameList= lnameTree.get(oldData);
          lnameList.remove(offset);
            if (lnameList.isEmpty()){
           lnameTree.remove(oldData);}
        else{
        lnameTree.put(oldData,lnameList);}
          
            if( lnameTree.containsKey(newData)){
                                ArrayList<Long> lastnameList= lnameTree.get(newData);
                                lastnameList.add(offset);
                                 lnameTree.put(newData, lastnameList);
                                }
                                
                                else{ 
                                   ArrayList<Long> lastnameList = new ArrayList<Long>();
                                   lastnameList.add(offset);
                                   lnameTree.put(newData, lastnameList);}
            
            writelnameFile(lnameTree);
       }
       
       if(index ==7)
       {
          ArrayList<Long> sList= stateTree.get(oldData);
          sList.remove(offset);
           if(sList.isEmpty()){
            stateTree.remove(oldData);
        }
        else{
        stateTree.put(oldData,sList);}
          
            if(stateTree.containsKey(newData)){
                                ArrayList<Long> stateList= stateTree.get(newData);
                                stateList.add(offset);
                                 stateTree.put(newData, stateList);
                                }
                                
                                else{ 
                                   ArrayList<Long> stateList = new ArrayList<Long>();
                                   stateList.add(offset);
                                   stateTree.put(newData, stateList);}
           
        writestateFile(stateTree);
       }
           
       }}
    
    else
        System.out.println("Record to modify does not exist");
    
             
      } 

     private static long getFilePointer(){
         try
        {
            RandomAccessFile file = new RandomAccessFile(databaseFile, "r");
            file.seek(file.length());
            long r=file.getFilePointer();
            file.close();
            return r;
        }catch(Exception ex)
        {
            return 0;
        }
     }
    private static void insert(String input) 
    {
       mainOffset=getFilePointer(); 
        
        System.out.println("\nInserting record: "+input);
        boolean flag=true;
        input =input.replace("'", "\"");
        String dataToWrite[] = input.split("\",\"|\",|,\"");
        input+="*\n";
       
         no_records++;
      //creating tree map
        for(int i=0;i<13;i++)
        {  
           dataToWrite[i]= dataToWrite[i].replace ("\"", "");
            switch(i)
            {
               
                    case 0:
                    if(idTree.containsKey(Integer.parseInt(dataToWrite[i])))
                    {
                         System.out.println("Key already exists");
                         i=13;
                         flag=false;
                         break;
                        
                      
                    }
                    else{
                        int id=Integer.parseInt(dataToWrite[i]);
                        idTree.put(id,mainOffset);
                        lastID=id;
                        break;
                        
                    }
                    case 2:
                        if( lnameTree.containsKey(dataToWrite[i]))
                        {
                        ArrayList<Long> lastnameList= lnameTree.get(dataToWrite[i]);
                        lastnameList.add(mainOffset);
                        lnameTree.put(dataToWrite[i], lastnameList);
                        }

                        else{ 
                           ArrayList<Long> lastnameList = new ArrayList<Long>();
                           lastnameList.add(mainOffset);
                           lnameTree.put(dataToWrite[i], lastnameList);}
                          break;
                    case 7:
                         //String temp2 = dataToWrite[i].replace ("\"", ""); 
                         if( stateTree.containsKey(dataToWrite[i])){
                        ArrayList<Long> stateList= stateTree.get(dataToWrite[i]);
                        stateList.add(mainOffset);
                        stateTree.put(dataToWrite[i], stateList);
                        }

                        else{ 
                           ArrayList<Long> stateList = new ArrayList<Long>();
                           stateList.add(mainOffset);
                           stateTree.put(dataToWrite[i], stateList);}
                           break;

                    default: continue;
            }     		
        } 
        
        if (flag){
        writeToRandomAcessFile(databaseFile,input,mainOffset);
        mainOffset+=input.length();
      //update ndx files
       //write id.ndx
        writeIndexFile(idTree);

          //write lastname.ndx file
        writelnameFile(lnameTree);

           //write state.ndx File
        writestateFile(stateTree);
         System.out.println("Insert Done......");
        }
      
    }
    
    private static String searchLname(long offset)
    {
        ArrayList<Long> lastnameList = new ArrayList<Long>();
        String lastNameKey;
        for(Map.Entry<String,ArrayList<Long>> entry : lnameTree.entrySet())
        {
            lastnameList = entry.getValue();
            if(lastnameList.contains(offset))
            {
                lastNameKey = entry.getKey();
                return lastNameKey;
            }
        }
        return null;
    }
    private static String searchState(long offset)
    {
        ArrayList<Long> stateList = new ArrayList<Long>();
        String stateKey;
        for(Map.Entry<String,ArrayList<Long>> entry : stateTree.entrySet())
        {
            stateList = entry.getValue();
            if(stateList.contains(offset))
            {
                stateKey = entry.getKey();
                return stateKey;
            }
        }
        return null;
    }
    private static void delete(int id) 
    {
        long delOffset = 0;
          System.out.println("\nDelete record with key "+ id +"... ");
        //delOffset = searchOffset(id);
        String last_name="";
        String state="", record="";
        if(!idTree.containsKey(id))
        {
            System.out.println("No Records Found!");
        }
        else{
        delOffset = idTree.get(id);
       
        last_name = searchLname(delOffset);
        state = searchState(delOffset);
         long tempOffset=delOffset;
         while(true){
            char print=readFromRandomAcessFile(databaseFile,tempOffset,1);
            if(print == '*')
            {break;}
            record+="0";
            //System.out.println(record);
            tempOffset++;
           }
         
        writeToRandomAcessFile(databaseFile,record,delOffset);
        idTree.remove(id);
        ArrayList<Long> lnameList= lnameTree.get(last_name);
        lnameList.remove(delOffset);
        if (lnameList.isEmpty()){
           lnameTree.remove(last_name);}
        else{
        lnameTree.put(last_name,lnameList);}
        
        ArrayList<Long> stateList= stateTree.get(state);
        stateList.remove(delOffset);
        if(stateList.isEmpty()){
            stateTree.remove(state);
        }
        else{
        stateTree.put(state,stateList);}
        //update ndx files
        writeIndexFile(idTree);
        writelnameFile(lnameTree);
        writestateFile(stateTree);
        System.out.println("Delete successful...");
        }
        
    }
    private static void deleteLname(String lastName) 
    {
        ArrayList<Long> lastnameList = new ArrayList<>();
        lastnameList = lnameTree.get(lastName);
        int id=-1;
        for (Long offset : lastnameList)
        {
            for(Map.Entry<Integer, Long> entry : idTree.entrySet())
            {
                if(Objects.equals(offset, entry.getValue()))
                {
                    id=entry.getKey();
                    break;
                }
            }
            delete(id);
        }
        
    }
    private static void deleteState(String state) 
    {
        ArrayList<Long> stateList = new ArrayList<>();
        stateList = stateTree.get(state);
        int id[] = new int[500];
        int counter=0;
        for (Long offset : stateList)
        {
            for(Map.Entry<Integer, Long> entry : idTree.entrySet())
            {
                if(Objects.equals(offset, entry.getValue()))
                {
                    id[counter++]=entry.getKey();
                    break;
                }
            }
        }
        for(int i=0;i<counter;i++)
            delete(id[i]);
    }

    private static void count() {
        int size=idTree.size();
        System.out.println("\nTotal number of entries in DataBase are: "+size);
    }

    private static void readIndexFiles() {
         try{
             String line;
         FileReader inputFile = new FileReader(idFile);     
         BufferedReader in = new BufferedReader(inputFile);
          while ((line = in.readLine()) != null) {
             line=line.trim();
             String dataToWrite[]=line.split(" ");    
				Long offset=Long.parseLong(dataToWrite[1]);
				idTree.put(Integer.parseInt(dataToWrite[0]),offset);
          
         }
          
           inputFile = new FileReader(stateFile);     
            in = new BufferedReader(inputFile);
            while ((line = in.readLine()) != null) {
             line=line.trim();
             String dataToWrite[]=line.split("=");
             String Data[]=dataToWrite[1].split(",");
              ArrayList<Long> lastnameList = new ArrayList<Long>();
             for(int i=0;i<Data.length;i++){
				Long offset=Long.parseLong(Data[i]);
                                   lastnameList.add(offset);
                                   stateTree.put(dataToWrite[0], lastnameList);
          }
              
         }
          inputFile.close();
          
           inputFile = new FileReader(lastNameFile);     
            in = new BufferedReader(inputFile);
           while ((line = in.readLine()) != null) {
             line=line.trim();
             String dataToWrite[]=line.split("=");
             String Data[]=dataToWrite[1].split(",");
             ArrayList<Long> lastnameList= new ArrayList<Long>();
             for(int i=0;i<Data.length;i++){
				Long offset=Long.parseLong(Data[i]);
                                 lastnameList.add(offset);
                                 lnameTree.put(dataToWrite[i], lastnameList);}
          
         }
          inputFile.close();
         }
         catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
}

