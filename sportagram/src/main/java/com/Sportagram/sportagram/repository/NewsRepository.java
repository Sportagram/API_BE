package com.Sportagram.sportagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Sportagram.sportagram.entity.News;


import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByTitle(String title);
    List<News> findByTeamNameAndTitle(String teamName, String title);
    List<News> findByNewsDateOrderByNewsDateDesc(String newsDate);
    List<News> findByTeamNameOrderByNewsDateDesc(String teamName);

    boolean existsByTeamNameAndTitle(String teamName, String title);

}
