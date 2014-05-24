package pl.piotrsukiennik.tuner.ai;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.ai.impl.SchedulingSelectionHelper;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

/**
 * @author Piotr Sukiennik
 * @date 23.05.14
 */
public class DataSourceSelectionHelperBuilder<T extends DataSourceSelectable> implements GenericBuilder<DataSourceSelectionHelper<T>> {

    private GenericBuilder<UpdateableSelectionHelper<T>> selectionHelperBuilder;

    private FitnessCalculator fitnessCalculator;

    public DataSourceSelectionHelperBuilder() {
    }

    DataSourceSelectionHelperBuilder( GenericBuilder<UpdateableSelectionHelper<T>> selectionHelperBuilder, FitnessCalculator fitnessCalculator ) {
        this.selectionHelperBuilder = selectionHelperBuilder;
        this.fitnessCalculator = fitnessCalculator;
    }

    public DataSourceSelectionHelperBuilder<T> withSelectionHelperBuilder( GenericBuilder<UpdateableSelectionHelper<T>> selectionHelperBuilder ) {
        this.selectionHelperBuilder = selectionHelperBuilder;
        return this;
    }

    public DataSourceSelectionHelperBuilder<T> withFitnessCalculator( FitnessCalculator fitnessCalculator ) {
        this.fitnessCalculator = fitnessCalculator;
        return this;
    }

    @Override
    public DataSourceSelectionHelper<T> build() {
        return new SchedulingSelectionHelper<T>(selectionHelperBuilder.build(),fitnessCalculator);
    }
}
