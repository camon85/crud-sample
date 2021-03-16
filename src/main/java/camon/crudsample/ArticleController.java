package camon.crudsample;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/article")
@RestController
@CrossOrigin("*")
public class ArticleController {

  private final ArticleRepository repository;

  public ArticleController(ArticleRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public Article save(@RequestBody Article article) {
    return repository.save(article);
  }

  @GetMapping
  public List<Article> findAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Article findById(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found"));
  }

  @PutMapping("/{id}")
  public Article modify(@PathVariable Long id, @RequestBody Article requestBody) {
    Optional<Article> optionalArticle = repository.findById(id);
    if (optionalArticle.isPresent()) {
      Article article = optionalArticle.get();
      article.setTitle(requestBody.getTitle());
      article.setContent(requestBody.getContent());
      return repository.save(article);
    } else {
      throw new IllegalArgumentException("Not Found");
    }
  }

  @DeleteMapping("/{id}")
  public Long deleteById(@PathVariable Long id) {
    Optional<Article> optionalArticle = repository.findById(id);
    if (optionalArticle.isPresent()) {
      repository.deleteById(id);
    }
    return id;
  }

}
