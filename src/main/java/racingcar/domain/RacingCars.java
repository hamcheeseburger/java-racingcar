package racingcar.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import racingcar.controller.MovingStrategy;
import racingcar.controller.RacingCarDto;

public class RacingCars {

    private static final String NAME_BLANK = " ";
    private static final String NAME_NOT_BLANK = "";
    private static final int EQUAL_SYMBOL_NUMBER = 0;

    private final List<RacingCar> cars;
    private final MovingStrategy movingStrategy;

    public RacingCars(String[] carNameArray, MovingStrategy movingStrategy) {
        this.cars = makeRacingCars(carNameArray);
        this.movingStrategy = movingStrategy;
        checkCarsEmpty();
    }

    private List<RacingCar> makeRacingCars(String[] carNames) {
        return Arrays.stream(carNames)
            .map(this::removeNameBlank)
            .map(RacingCar::new)
            .collect(Collectors.toList());
    }

    private void checkCarsEmpty() {
        if (cars.isEmpty()) {
            throw new RuntimeException("최소 1개의 자동차가 존재해야 한다.");
        }
    }

    private String removeNameBlank(String name) {
        return name.replaceAll(NAME_BLANK, NAME_NOT_BLANK);
    }

    public List<RacingCarDto> getRacingCarDtoList() {
        return cars.stream()
            .map(RacingCarDto::from)
            .collect(Collectors.toList());
    }

    public void moveCars() {
        cars.forEach(this::moveCar);
    }

    private void moveCar(RacingCar car) {
        if (movingStrategy.isMovable()) {
            car.move();
        }
    }

    public List<String> searchWinnerNames() {
        RacingCar anyWinner = searchAnyWinner();
        return cars.stream()
            .filter(car -> isSamePosition(car, anyWinner))
            .map(RacingCar::getName)
            .collect(Collectors.toList());
    }

    private boolean isSamePosition(RacingCar racingCar, RacingCar anyWinner) {
        return racingCar.compareTo(anyWinner) == EQUAL_SYMBOL_NUMBER;
    }

    private RacingCar searchAnyWinner() {
        return cars.stream()
            .max(RacingCar::compareTo)
            .orElseThrow(() -> new RuntimeException("승자를 찾을 수 없다."));
    }

}
