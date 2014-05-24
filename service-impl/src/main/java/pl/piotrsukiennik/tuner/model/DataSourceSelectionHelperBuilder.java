package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.service.DataSourceFitnessService;
import pl.piotrsukiennik.tuner.service.DataSourceSelectable;
import pl.piotrsukiennik.tuner.service.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

/**
 * @author Piotr Sukiennik
 * @date 23.05.14
 */
public class DataSourceSelectionHelperBuilder<T extends DataSourceSelectable> implements GenericBuilder<DataSourceSelectionHelper<T>> {

    private GenericBuilder<UpdateableSelectionHelper<T>> selectionHelperBuilder;

    private DataSourceFitnessService dataSourceFitnessService;

    public DataSourceSelectionHelperBuilder() {
    }

    DataSourceSelectionHelperBuilder( GenericBuilder<UpdateableSelectionHelper<T>> selectionHelperBuilder, DataSourceFitnessService dataSourceFitnessService ) {
        this.selectionHelperBuilder = selectionHelperBuilder;
        this.dataSourceFitnessService = dataSourceFitnessService;
    }

    public DataSourceSelectionHelperBuilder<T> withSelectionHelperBuilder( GenericBuilder<UpdateableSelectionHelper<T>> selectionHelperBuilder ) {
        this.selectionHelperBuilder = selectionHelperBuilder;
        return this;
    }

    public DataSourceSelectionHelperBuilder<T> withFitnessCalculator( DataSourceFitnessService dataSourceFitnessService ) {
        this.dataSourceFitnessService = dataSourceFitnessService;
        return this;
    }

    @Override
    public DataSourceSelectionHelper<T> build() {
        return new DataSourceSelectionHelperImpl<T>(selectionHelperBuilder.build(), dataSourceFitnessService );
    }
}
