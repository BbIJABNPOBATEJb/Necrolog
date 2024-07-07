package me.bbijabnpobatejb.necrolog.enums;

import static me.bbijabnpobatejb.necrolog.object.PluginHelper.getPlugin;

public enum Message {
    DONT_HAVE_PERMS("§cYou do not have permission","§сНедостаточно прав"),
    NEXT_PAGE("Next page","Следующая страница"),
    PREV_PAGE("Previous page","Предыдущая страница"),
    PAGE("Page","Страница"),
    DEATH_LIST("List of deaths (Total ","Список смертей (Всего "),
    PAGE_NOT_FOUND("§cPage not found, use 1 to ","§cСтраница не найден, используйте от 1 до "),
    NECROLOG_IS_EMPTY("§cNecrolog is empty","§cНекролог пуст"),
    RELOAD_CFG("§3Reload config","§3Перезагрузка конфига"),
    USAGE("§сUsage ","§cИспользуйте "),
    ERROR_WRITE("§sError deathInfo == null. Failed to record player's death information ","§cОшибка deathInfo == null. Не удалось записать информацию о смерти игрока "),
    PLUGIN_START("The plugin is running","Плагин запущен"),
    PLUGIN_DISABLE("The plugin is off","Плагин выключен"),
    CLEARED("§eAll death records have been cleared","§eВсе записи о смертях очищены"),
    DO_YOU_WANT("§eDo you really want to purge all death records? (Total ","§eВы действительно хотите очистить все записи о смертях? (Всего "),
    THEN_USAGE("§eThen use","§eТогда используйте");


    private final String en;
    private final String ru;

    Message(String en, String ru) {
        this.en = en;
        this.ru = ru;
    }

    public String getMessage(){
        switch (getPlugin().language()){
            case ru:
                return ru;
            default:
                return en;
        }
    }
}
