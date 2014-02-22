package pl.piotrsukiennik.tuner.service.parser;

import pl.piotrsukiennik.tuner.model.source.TableSource;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface TableSourceManager {
    TableSource mergeTableSource( TableSource tableSource );

    TableSource getLastAttachedTableSource();
}
