package jp.co.infitech.java.astah;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.change_vision.jude.api.inf.model.IERDomain;
import com.change_vision.jude.api.inf.model.INamedElement;

public class DomainToHTML {

    public static void makeHtml(INamedElement[] domainList, File outputDirectory) {
        Path path = Paths.get(outputDirectory.toString(), "domain.html");
        System.out.println("出力 : " + path);

        List<INamedElement> elementList = new ArrayList<INamedElement>();
        for(INamedElement ine : domainList) {
            elementList.add(ine);
        }
        Collections.sort(elementList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                IERDomain en1 = (IERDomain)o1;
                IERDomain en2 = (IERDomain)o2;
                return en1.getPhysicalName().toLowerCase().compareTo(en2.getPhysicalName().toLowerCase());
            }
        });

        List<String> html = new ArrayList<>();
        html.add("<!DOCTYPE html>");
        html.add("<html>");
        html.add("<head>");
        html.add("<meta charset=\"UTF-8\">");
        html.add("<title>" + HtmlWord.getWord("entity_list") + "</title>");
        html.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"./index.css\">");
        html.add("</head>");
        html.add("<body>");
        html.add("<a href = \"./index.html\">to " + HtmlWord.getWord("entity_list") + "</a>");
        html.add("<br>");
        html.add("<br>");
        html.add("<h1>" + HtmlWord.getWord("domain_list") + "</h1>");
        html.add("<br>");
        html.add("<table>");
        html.add("<tr><th>" + HtmlWord.getWord("logical_name") +
                "</th><th>" + HtmlWord.getWord("physical_name") +
                "</th><th>" + HtmlWord.getWord("type") +
                "</th><th>" + HtmlWord.getWord("length_precision") +
                "</th><th>" + HtmlWord.getWord("default_value") +
                "</th><th>" + HtmlWord.getWord("definition") +
                "</th></tr>");
        for(INamedElement ine : elementList) {
            IERDomain domain= (IERDomain)ine;
            StringBuilder line = new StringBuilder();
            line.append("<tr><td>");
            line.append(domain.getLogicalName());
            line.append("</td><td>");
            line.append("<a href = \"./entity/" + domain.getPhysicalName() + ".html\">" + domain.getPhysicalName() + "</a>");
            line.append("</td><td>");
            line.append(domain.getDatatypeName());
            line.append("</td><td>");
            line.append(domain.getLengthPrecision() != null ? domain.getLengthPrecision() : "");
            line.append("</td><td>");
            line.append(domain.getDefaultValue() != null ? domain.getDefaultValue() : "");
            line.append("</td><td>");
            line.append(domain.getDefinition() != null ? domain.getDefinition().replaceAll("\\r\\n|\\r|\\n", "<br />") : "");
            line.append("</td></tr>");
            html.add(line.toString());
        }
        html.add("</table>");
        html.add("<br>");
        html.add("<br>");
        html.add("<a href = \"./index.html\">to " + HtmlWord.getWord("entity_list") + "</a>");
        html.add("</body>");
        html.add("</html>");

        try {
            Files.write(path, html, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
