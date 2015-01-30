/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package librarymanagementsystem;

/**
 *
 * @author swetalina
 */
public class AuthorName {
    
    public String name;
    public int type;
     
    
    public AuthorName(String Name){
        
       // name=Name;
        name=Name;
       // String nameOfAuthor[] = name.split(", ");
                             
        if (name.equals("Various")||name.equals("The Beatles"))
               {
                  type=2;
               }
               else type=1;
        
        
    }
    
}
