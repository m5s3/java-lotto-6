package lotto.service;

import static lotto.domain.Lotto.LOTTO_PRICE;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotto.domain.Lotto;
import lotto.domain.LottoGenerator;
import lotto.domain.WinningPrize;

public class LottoService {
    private final Lotto winningNumbers;

    public LottoService(Lotto winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    public List<Lotto> createLottos(int lottoPurchaseAmount) {
        int lottoCount = calculateLottoCount(lottoPurchaseAmount);
        return Stream.generate(LottoGenerator::generatorLotto)
                .limit(lottoCount)
                .collect(Collectors.toList());
    }

    public double getReturnOnLotto(List<Lotto> lottos, int lottoPurchaseAmount, int bonusNumber) {
        Map<WinningPrize, Integer> winningPrizes = getWinningPrizes(lottos, bonusNumber);
        int winningPrizeAmount = sumWinningPrizeAmount(winningPrizes);
        double returnOnLotto = calculateReturnOnLotto(winningPrizeAmount, lottoPurchaseAmount);
        return roundSecondPoint(returnOnLotto);
    }

    public Map<WinningPrize, Integer> getWinningPrizes(List<Lotto> lottos, int bonusNumber) {
        return lottos.stream()
                .map(lotto -> {
                    int winningNumbersCount = checkLotto(lotto);
                    boolean existBonusNumber = checkBonusNumber(lotto, bonusNumber);
                    return WinningPrize.valueOf(winningNumbersCount, existBonusNumber);
                })
                .collect(
                        Collectors.toMap(winningPrize -> winningPrize, winningPrize -> 1, Integer::sum)
                );
    }

    public double calculateReturnOnLotto(int winningPrizeAmount, int lottoPurchaseAmount) {
        return (double) winningPrizeAmount / lottoPurchaseAmount * 100;
    }

    private int sumWinningPrizeAmount(Map<WinningPrize, Integer> winningPrizes) {
        return winningPrizes.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getWinningPrizeAmount() * entry.getValue())
                .sum();
    }

    private double roundSecondPoint(double amount) {
        return (double) Math.round(amount * 10) / 10;
    }

    public int checkLotto(Lotto lotto) {
        return winningNumbers.compareNumbers(lotto);
    }

    public boolean checkBonusNumber(Lotto lotto, int bonusNumber) {
        return lotto.compareNumber(bonusNumber);
    }

    private int calculateLottoCount(int lottoPurchaseAmount) {
        return lottoPurchaseAmount / LOTTO_PRICE;
    }
}
