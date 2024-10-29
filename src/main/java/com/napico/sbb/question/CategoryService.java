package com.napico.sbb.question;

import com.napico.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getList() {
        return this.categoryRepository.findAll();
    }
    public Category getCategory(Integer id) {
        Optional<Category> c = this.categoryRepository.findById(id);
        if (c.isPresent()) {
            return c.get();
        } else {
            throw new DataNotFoundException("category not found");
        }
    }
}
