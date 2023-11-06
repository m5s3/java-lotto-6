package lotto.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoServiceTest {
    @DisplayName("로또 구입 금액 만큼 로또를 생성한다")
    @Test
    void createLottosByLottoPurchaseAmount() {
        final int lottoPurchaseAmount = 5000;
        Lotto winningNumbers = new Lotto(List.of(1,2,3,4,5,6));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        List<Lotto> lottos = lottoService.createLottos(lottoPurchaseAmount);

        final int expectedLottosSize = 5;
        assertThat(lottos.size()).isEqualTo(expectedLottosSize);
    }

    @DisplayName("당첨번호가 1개이다.")
    @Test
    void oneWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(11,22,33,42,43,45));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(12,25,36,37,38,45));
        int winningCount = lottoService.checkLotto(lotto);
        final int expectedWinningCount = 1;
        assertThat(winningCount).isEqualTo(expectedWinningCount);
    }

    @DisplayName("당첨번호가 3개이다.")
    @Test
    void threeWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(1,2,3,4,5,6));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(1,2,3,7,8,9));
        int winningCount = lottoService.checkLotto(lotto);
        final int expectedWinningCount = 3;
        assertThat(winningCount).isEqualTo(expectedWinningCount);
    }

    @DisplayName("당첨번호가 4개이다.")
    @Test
    void fourWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(1,5,12,31,34,45));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(1,12,31,32,36,45));
        int winningCount = lottoService.checkLotto(lotto);
        final int expectedWinningCount = 4;
        assertThat(winningCount).isEqualTo(expectedWinningCount);
    }

    @DisplayName("당첨번호가 5개이다.")
    @Test
    void fiveWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(1,3,12,21,22,45));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(1,3,11,21,22,45));
        int winningCount = lottoService.checkLotto(lotto);
        final int expectedWinningCount = 5;
        assertThat(winningCount).isEqualTo(expectedWinningCount);
    }

    @DisplayName("당첨번호가 6개(모두) 이다.")
    @Test
    void allWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(1,17,20,21,30,42));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(1,17,20,21,30,42));
        int winningCount = lottoService.checkLotto(lotto);
        final int expectedWinningCount = 6;
        assertThat(winningCount).isEqualTo(expectedWinningCount);
    }

    @DisplayName("당첨번호가 0개이다")
    @Test
    void notSameAllWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(1,17,20,21,30,42));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(2,22,32,34,41,45));
        int winningCount = lottoService.checkLotto(lotto);
        final int expectedWinningCount = 0;
        assertThat(winningCount).isEqualTo(expectedWinningCount);
    }

    @DisplayName("보너스 번호를 맞췄다.")
    @Test
    void containBonusNumber() {
        Lotto winningNumbers = new Lotto(List.of(1,17,20,21,30,42));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(2,7,22,34,41,45));
        boolean isContainNumber = lottoService.checkBonusNumber(lotto);
        assertThat(isContainNumber).isTrue();
    }

    @DisplayName("보너스 번호가 맞추지 못했다.")
    @Test
    void notContainBonusNumber() {
        Lotto winningNumbers = new Lotto(List.of(1,17,20,21,30,42));
        LottoService lottoService = new LottoService(winningNumbers, 7);
        Lotto lotto = new Lotto(List.of(2,9,22,34,41,45));
        boolean isContainNumber = lottoService.checkBonusNumber(lotto);
        assertThat(isContainNumber).isFalse();
    }
}