package com.saveme.ledger.service.process;

import com.saveme.global.error.exception.BusinessException;
import com.saveme.global.error.ErrorCode;
import com.saveme.ledger.dto.response.CategoryStatisticResponseDto;
import com.saveme.ledger.dto.response.StatisticsViewResponseDto;
import com.saveme.ledger.service.query.StatisticsQueryService;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsProcessService {

    private final StatisticsQueryService statisticsQueryService;

    @Transactional(readOnly = true)
    public StatisticsViewResponseDto getStatisticsView(Long memberId, String yearMonthStr) {
        YearMonth currentMonth;
        try {
            currentMonth = (yearMonthStr == null) ? YearMonth.now() : YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ErrorCode.INVALID_DATE_FORMAT);
        }
        String current = currentMonth.toString();
        String prev = currentMonth.minusMonths(1).toString();
        String next = currentMonth.plusMonths(1).toString();

        List<CategoryStatisticResponseDto> stats = statisticsQueryService.getMonthlyStatistics(memberId, current);

        return StatisticsViewResponseDto.of(current, prev, next, stats);
    }
}