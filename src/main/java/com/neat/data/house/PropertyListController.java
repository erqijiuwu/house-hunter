package com.neat.data.house;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;

import com.neat.data.Main;

@Service
public class PropertyListController {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final String https_url = "https://www.trademe.co.nz/browse/categoryattributesearchresults.aspx?cid=5748&search=1&nofilters=1&originalsidebar=1&rptpath=350-5748-&rsqid=f57ddb79c3b34ddbb04f35d99443ea5b&key=267294992&sort_order=property_feature";

    @Bean
    public CommandLineRunner demo(PropertyListRepository repository) {
        return (args) -> {
            int page = 0;
            while (true) {
                page++;
                try {
                    getPropertiesListUrl(repository, page);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    break;
                }
            }
        };
    }

    private static void getPropertiesListUrl(PropertyListRepository repository, int page) throws IOException {
        String url = String.format("%s&page=%d", https_url, page);
        log.info(url);
        Document doc = Jsoup.connect(url).get();
        Elements detailPages = doc.select("#ListViewList a");

        detailPages.forEach(element -> {
            String id = element.attr("id");
            String href = element.attr("href");
            PropertyListEntity bean = new PropertyListEntity();
            bean.setId(id);
            bean.setBase_url(element.baseUri());
            bean.setUrl(href);
            repository.saveAndFlush(bean);
        });
    }
}


@Entity
@Table(name = "property_list")
class PropertyListEntity {
    @Id
    private String id;

    @Column(columnDefinition = "text")
    private String base_url;

    @Column(columnDefinition = "text")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String toString() {
        return String.format("id: %s,base_url=%s, url=%s", this.id, this.base_url, this.url);
    }
}


@Repository
interface PropertyListRepository extends JpaRepository<PropertyListEntity, Long> {
}
