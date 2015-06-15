/**
 *    OpenTrainingCenter
 *
 *    Copyright (C) 2014 Sascha Iseli sascha.iseli(at)gmx.ch
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.opentrainingcenter.server.service.db.internal.criteria;

import ch.opentrainingcenter.server.service.db.ISearchCriteria;
import ch.opentrainingcenter.transfer.ITraining;

public class DistanceCriteria implements ISearchCriteria {

    private final int laenge;

    public DistanceCriteria(final int laenge) {
        this.laenge = laenge;
    }

    @Override
    public boolean matches(final ITraining training) {
        return training.getLaengeInMeter() >= laenge;
    }

}
