package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Held Item")
@Description("A citizen's held item")
@Examples("Set last spawned citizen's held item to wooden sword")
@Since("1.0.0")

public class ExprHeldItem extends SimplePropertyExpression<NPC, ItemStack> {

    static {
        register(ExprHeldItem.class, ItemStack.class, "(citizen|npc) (held item|tool)", "npcs");
    }
    @Override
    public @Nullable ItemStack convert(NPC npc) {
        return npc.getOrAddTrait(Equipment.class).get(EquipmentSlot.HAND);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) return CollectionUtils.array(ItemStack.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null && delta[0] instanceof ItemStack) {
            ItemStack tool = new ItemStack((ItemStack) delta[0]);
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, tool);
            }
        }
    }

    @Override
    @NonNull
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }
    @Override
    protected String getPropertyName() {
        return "Citizen's held item";
    }

}
