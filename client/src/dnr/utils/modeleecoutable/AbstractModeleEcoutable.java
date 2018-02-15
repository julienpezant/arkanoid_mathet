package dnr.utils.modeleecoutable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable{

	private List<EcouteurModele> ecouteurs = new ArrayList<EcouteurModele>();
	
	@Override
	public void ajoutEcouteur(EcouteurModele e) {
		ecouteurs.add(e);
		e.modeleMAJ(this);
	}

	@Override
	public void retraitEcouteur(EcouteurModele e) {
		ecouteurs.remove(e);
	}
	
	protected void fireChangement(){
		for(EcouteurModele e : ecouteurs)
			e.modeleMAJ(this);
	}

}
