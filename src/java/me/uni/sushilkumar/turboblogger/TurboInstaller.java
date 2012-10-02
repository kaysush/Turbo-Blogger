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
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Sushil Kumar <0120sushil@gmail.com>
 */
@ManagedBean(name = "installer")
@ViewScoped
public class TurboInstaller implements Serializable {

    /*Turbo Setting*/
    private String title;
    private String email;
    private String turboName;
    private String turboPassword;
    /*DB Credentials*/
    private String dbUsername;
    private String dbPassword;
    private String dbHost;
    private String dbName;
    /*Already Configured DB Credentials*/
    private String acdbUsername;
    private String acdbPassword;
    private String acdbHost;
    private String acdbName;
    private boolean isAlreadyConfigured;

    /** Creates a new instance of TurboInstaller */
    public TurboInstaller() {
        this.title = "Amazing Turbo Blog";
        this.isAlreadyConfigured = false;
        alreadyConfigured();
    }

    public void installDB(ActionEvent event) {
        System.out.println("Finally I'm relaxed that i'm called");


        TurboDBManager manager = new TurboDBManager(this.dbHost, this.dbUsername, this.dbPassword, this.dbName);
        boolean validDB = manager.isValidDB();
        if (validDB) {
            saveCredentials();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Database Configuration Complete"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong Databse Credentials", ""));
        }

    }

    public void saveCredentials() {
        try {
            String installerFilePath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/configs/config.xml");
            File file = new File(installerFilePath);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("credentials");
            doc.appendChild(root);
            Element edbHost = doc.createElement("host");
            Element edbUsername = doc.createElement("username");
            Element edbPassword = doc.createElement("password");
            Element edbName = doc.createElement("database");
            edbHost.appendChild(doc.createTextNode(dbHost));
            edbUsername.appendChild(doc.createTextNode(dbUsername));
            edbPassword.appendChild(doc.createTextNode(dbPassword));
            edbName.appendChild(doc.createTextNode(dbName));
            root.appendChild(edbHost);
            root.appendChild(edbUsername);
            root.appendChild(edbPassword);
            root.appendChild(edbName);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Internal Server Error", ""));
            Logger.getLogger(TurboInstaller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Internal Server Error", ""));
            Logger.getLogger(TurboInstaller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void alreadyConfigured() {
        InputStream in = null;
        try {
            String installerFilePath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/configs/config.xml");
            File file = new File(installerFilePath);
            if (file.exists()) {
                in = new FileInputStream(file);
                org.jsoup.nodes.Document doc = Jsoup.parse(in, "UTF-8", "", Parser.xmlParser());
                org.jsoup.nodes.Element credentials = doc.getElementsByTag("credentials").get(0);
                if (credentials != null) {
                    this.isAlreadyConfigured = true;
                    this.dbHost = credentials.getAllElements().get(1).text();
                    this.dbUsername = credentials.getAllElements().get(2).text();
                    this.dbPassword = credentials.getAllElements().get(3).text();
                    this.dbName = credentials.getAllElements().get(4).text();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TurboInstaller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TurboInstaller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void configureSettings(ActionEvent event) {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDbUsername(String username) {
        this.dbUsername = username;
    }

    public String getDbUsername() {
        return this.dbUsername;
    }

    public void setDbPassword(String password) {
        this.dbPassword = password;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setTurboName(String turboName) {
        this.turboName = turboName;
    }

    public String getTurboName() {
        return this.turboName;
    }

    public void setTurboPassword(String turboPassword) {
        this.turboPassword = turboPassword;
    }

    public String getTurboPassword() {
        return this.turboPassword;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbHost() {
        return this.dbHost;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return this.dbName;
    }
}
