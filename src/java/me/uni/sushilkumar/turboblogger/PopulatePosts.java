/*
 * Copyright Sushil Kumar <0120sushil@gmail.com>.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
package me.uni.sushilkumar.turboblogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author Sushil Kumar <0120sushil@gmail.com>
 */
@ManagedBean(name = "posts")
@ViewScoped
public class PopulatePosts {
    private ArrayList<TurboPost> postList;
    private String title;

    /** Creates a new instance of PopulatePosts */
    public PopulatePosts() {
        postList=new ArrayList<TurboPost>();
        this.title="Turbo Blogger";
        init();
    }
    
    public final void init() 
    {
        try {
            //InputStream in=this.getClass().getResourceAsStream("posts.xml");
            String configFilePath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/configs/posts.xml");
            File file=new File(configFilePath);
            InputStream in=new FileInputStream(file);
            Document doc=Jsoup.parse(in,"UTF-8","",Parser.xmlParser());
            Elements posts=doc.getElementsByTag("post");
            for(Element post:posts)
            {
                Element postTitle=post.getAllElements().get(1);
                Element body=post.getAllElements().get(2);
                Element id=post.getAllElements().get(3);
                postList.add(new TurboPost(postTitle.text(),body.text(),id.text(),new Date()));
                
            }
        } catch (IOException ex) {
            Logger.getLogger(PopulatePosts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setPostList(ArrayList<TurboPost> postList)
    {
        this.postList=postList;
    }
    
    public ArrayList<TurboPost> getPostList()
    {
        return this.postList;
    }
    
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getTitle()
    {
        return this.title;
    }
    
}
