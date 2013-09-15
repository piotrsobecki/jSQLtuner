package pl.piotrsukiennik.tuner.cache.model;

import pl.piotrsukiennik.tuner.cache.model.build.EnterableHolderFactory;

import java.util.Collection;
import java.util.Collections;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:39
 */
public class HashesHolderManager<P,K> {
    private EnterableHolderFactory enterableHolderFactory;
    private EnterableHolder<P,K> rootHolder;

    public HashesHolderManager(EnterableHolderFactory enterableHolderFactory) {
        this.rootHolder = enterableHolderFactory.buildEnterable();
        this.enterableHolderFactory=enterableHolderFactory;
    }

    public void put(P[] path, K hash){
        Holder holder = rootHolder;
        for (int i=0; i<path.length;i++){
            if (holder instanceof EnterableHolder){
                Holder holderNew = ((EnterableHolder<P,K>)holder).enter(path[i]);
                if (holderNew==null){
                    holderNew  =  enterableHolderFactory.buildEnterable();
                    ((EnterableHolder)holder).put(path[i],holderNew);
                }
                holder=holderNew;
            }
            holder.add(hash);
        }
    }
    public Collection<K> get(P[] path){
        Holder holder = rootHolder;
        for (P str:path){
            if (holder instanceof EnterableHolder){
                holder = ((EnterableHolder<P,K>)holder).enter(str);
            }
        }
        return holder.get();
    }
    public Collection<K> getRemove(P[] path){
        Holder holder = rootHolder;
        for (P str:path){
            if (holder!=null && holder instanceof EnterableHolder){
                holder = ((EnterableHolder<P,K>)holder).enter(str);
            }
        }
        if (holder!=null){
            Collection<K> objects =   holder.get();
            rootHolder.remove(objects);
            return objects;
        }
        return Collections.EMPTY_LIST;
    }


}
