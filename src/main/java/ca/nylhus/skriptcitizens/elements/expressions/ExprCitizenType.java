package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.bukkitutil.EntityUtils;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen EntityType")
@Description("Get or set the entity type of a citizen.")
@Examples({"if citizen type of {_npc} = sheep:",
        "set citizen type of {_npc} to zombie"})
@Since("1.0.0")
@SuppressWarnings("rawtypes")
public class ExprCitizenType extends SimplePropertyExpression<NPC, EntityData> {

    static {
        register(ExprCitizenType.class, EntityData.class, "(citizen|npc) type", "npcs");
    }

    @Override
    public @Nullable EntityData<?> convert(NPC npc) {
        return EntityUtils.toSkriptEntityData(npc.getEntity().getType());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) return CollectionUtils.array(EntityData.class);
        return null;
    }

    @SuppressWarnings({"ConstantValue", "NullableProblems"})
    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null && delta[0] instanceof EntityData) {
            EntityData<?> entityData = (EntityData<?>) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                EntityType entityType = EntityUtils.toBukkitEntityType(entityData);
                npc.setBukkitEntityType(entityType);
            }
        }
    }

    @Override
    @NonNull
    public Class<? extends EntityData> getReturnType() {
        return EntityData.class;
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        return "citizen type";
    }

}
