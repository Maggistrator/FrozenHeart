package logic.spells;

import java.util.List;

import org.newdawn.slick.geom.Polygon;

import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.entity.Monster;
import logic.entity.StarlightGlimmer;

public class Spell {
	/**
	 * TODO: разработать отдельные обобщёные классы(или взять из предыдущих работ) игрока и монстра -
	 * только так можно создать ххороший абстрактный класс заклинания
	 * TODO: впилить drawingHierarchySorter, иначе всё поедет по бороде на первом же снеговике
	 * */
	
	/**для направленных одиночных заклинаний, вроже фаэрболов или лечения*/
	public void cast(StarlightGlimmer caster, Entity target, float value) {}
	
	/**для заклинаний, не имеющих цели - телепорт, силовой барьер, мины и т.д.*/
	public void cast(StarlightGlimmer caster, int x, int y) {}
	
	/**для AOE-заклинаний - взрывов, лучей, облаков газов и луж кислот*/
	public void cast(StarlightGlimmer caster, Polygon area, int value) {
		World world = caster.world;
		List<Entity> entities = world.getEntities("Enemy");
		entities.forEach((en)->{
			if(en.intersect(area) != null) {
				Monster monster = (Monster)en;
				monster.getHitted(value);
			}
		});
	}
	
}
