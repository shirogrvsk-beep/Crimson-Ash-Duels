private StatsManager statsManager;

@Override
public void onEnable() {
    getLogger().info("Crimson Ash Duels enabled!");

    saveDefaultConfig();
    loadArenas();

    duelManager = new DuelManager();
    matchManager = new MatchManager();
    cooldownManager = new CooldownManager(30);
    spectatorManager = new SpectatorManager();
    queueManager = new QueueManager(matchManager, this);
    statsManager = new StatsManager();

    getCommand("duel").setExecutor(new DuelCommand(duelManager, cooldownManager));
    getCommand("duelaccept").setExecutor(new DuelAcceptCommand(duelManager, matchManager, this));
    getCommand("spectate").setExecutor(new SpectateCommand(spectatorManager));
    getCommand("queue").setExecutor(new QueueCommand(queueManager));

    getServer().getPluginManager().registerEvents(new DuelListener(matchManager), this);
}

public StatsManager getStatsManager() {
    return statsManager;
}
