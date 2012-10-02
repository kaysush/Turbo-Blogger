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

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Sushil Kumar <0120sushil@gmail.com>
 */
public class TurboPost implements Serializable {
    private String title;
    private String body;
    private String permalink;
    private String id;
    private Date postDate;
    
    public TurboPost()
    {
    }
    
    public TurboPost(String title,String body,String id,Date postDate)
    {
        this.title=title;
        this.body=body;
        this.id=id;
        this.permalink="post/"+id;
        this.postDate=postDate;
    }
    
    public void setTitle(String title)
    {
        this.title=title;
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public void setBody(String body)
    {
        this.body=body;
    }
    
    public String getBody()
    {
        return this.body;
    }
    public void setPermalink(String permalink)
    {
        this.permalink=permalink;
    }
    public String getPermalink()
    {
        return this.permalink;
    }
    public void setId(String id)
    {
        this.id=id;
    }
    public String getId()
    {
        return this.id;
    }
    public void setPostDate(Date postDate)
    {
        this.postDate=postDate;
    }
    public Date getPostDate()
    {
        return this.postDate;
    }
}
