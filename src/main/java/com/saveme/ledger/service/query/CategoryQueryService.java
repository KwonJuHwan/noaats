package com.saveme.ledger.service.query;

import com.saveme.ledger.dto.response.CategoryDto;
import com.saveme.ledger.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getRootCategories() {
        return categoryRepository.findAllByParentIsNull().stream()
            .map(CategoryDto::new)
            .toList();
    }

    // 대분류 ID로 소분류 목록 가져오기
    public List<CategoryDto> getChildCategories(Long parentId) {
        return categoryRepository.findAllByParentId(parentId).stream()
            .map(CategoryDto::new)
            .toList();
    }
}
