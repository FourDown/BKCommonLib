package com.bergerkiller.bukkit.common.protocol;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.v1_4_R1.Packet;
import net.minecraft.server.v1_4_R1.Packet0KeepAlive;
import net.minecraft.server.v1_4_R1.Packet100OpenWindow;
import net.minecraft.server.v1_4_R1.Packet101CloseWindow;
import net.minecraft.server.v1_4_R1.Packet102WindowClick;
import net.minecraft.server.v1_4_R1.Packet103SetSlot;
import net.minecraft.server.v1_4_R1.Packet104WindowItems;
import net.minecraft.server.v1_4_R1.Packet105CraftProgressBar;
import net.minecraft.server.v1_4_R1.Packet106Transaction;
import net.minecraft.server.v1_4_R1.Packet107SetCreativeSlot;
import net.minecraft.server.v1_4_R1.Packet108ButtonClick;
import net.minecraft.server.v1_4_R1.Packet10Flying;
import net.minecraft.server.v1_4_R1.Packet11PlayerPosition;
import net.minecraft.server.v1_4_R1.Packet12PlayerLook;
import net.minecraft.server.v1_4_R1.Packet130UpdateSign;
import net.minecraft.server.v1_4_R1.Packet131ItemData;
import net.minecraft.server.v1_4_R1.Packet132TileEntityData;
import net.minecraft.server.v1_4_R1.Packet13PlayerLookMove;
import net.minecraft.server.v1_4_R1.Packet14BlockDig;
import net.minecraft.server.v1_4_R1.Packet15Place;
import net.minecraft.server.v1_4_R1.Packet16BlockItemSwitch;
import net.minecraft.server.v1_4_R1.Packet17EntityLocationAction;
import net.minecraft.server.v1_4_R1.Packet18ArmAnimation;
import net.minecraft.server.v1_4_R1.Packet19EntityAction;
import net.minecraft.server.v1_4_R1.Packet1Login;
import net.minecraft.server.v1_4_R1.Packet200Statistic;
import net.minecraft.server.v1_4_R1.Packet201PlayerInfo;
import net.minecraft.server.v1_4_R1.Packet202Abilities;
import net.minecraft.server.v1_4_R1.Packet203TabComplete;
import net.minecraft.server.v1_4_R1.Packet204LocaleAndViewDistance;
import net.minecraft.server.v1_4_R1.Packet205ClientCommand;
import net.minecraft.server.v1_4_R1.Packet20NamedEntitySpawn;
import net.minecraft.server.v1_4_R1.Packet22Collect;
import net.minecraft.server.v1_4_R1.Packet23VehicleSpawn;
import net.minecraft.server.v1_4_R1.Packet24MobSpawn;
import net.minecraft.server.v1_4_R1.Packet250CustomPayload;
import net.minecraft.server.v1_4_R1.Packet252KeyResponse;
import net.minecraft.server.v1_4_R1.Packet253KeyRequest;
import net.minecraft.server.v1_4_R1.Packet254GetInfo;
import net.minecraft.server.v1_4_R1.Packet255KickDisconnect;
import net.minecraft.server.v1_4_R1.Packet25EntityPainting;
import net.minecraft.server.v1_4_R1.Packet26AddExpOrb;
import net.minecraft.server.v1_4_R1.Packet28EntityVelocity;
import net.minecraft.server.v1_4_R1.Packet29DestroyEntity;
import net.minecraft.server.v1_4_R1.Packet2Handshake;
import net.minecraft.server.v1_4_R1.Packet30Entity;
import net.minecraft.server.v1_4_R1.Packet31RelEntityMove;
import net.minecraft.server.v1_4_R1.Packet32EntityLook;
import net.minecraft.server.v1_4_R1.Packet33RelEntityMoveLook;
import net.minecraft.server.v1_4_R1.Packet34EntityTeleport;
import net.minecraft.server.v1_4_R1.Packet35EntityHeadRotation;
import net.minecraft.server.v1_4_R1.Packet38EntityStatus;
import net.minecraft.server.v1_4_R1.Packet39AttachEntity;
import net.minecraft.server.v1_4_R1.Packet3Chat;
import net.minecraft.server.v1_4_R1.Packet40EntityMetadata;
import net.minecraft.server.v1_4_R1.Packet41MobEffect;
import net.minecraft.server.v1_4_R1.Packet42RemoveMobEffect;
import net.minecraft.server.v1_4_R1.Packet43SetExperience;
import net.minecraft.server.v1_4_R1.Packet4UpdateTime;
import net.minecraft.server.v1_4_R1.Packet51MapChunk;
import net.minecraft.server.v1_4_R1.Packet52MultiBlockChange;
import net.minecraft.server.v1_4_R1.Packet53BlockChange;
import net.minecraft.server.v1_4_R1.Packet54PlayNoteBlock;
import net.minecraft.server.v1_4_R1.Packet55BlockBreakAnimation;
import net.minecraft.server.v1_4_R1.Packet56MapChunkBulk;
import net.minecraft.server.v1_4_R1.Packet5EntityEquipment;
import net.minecraft.server.v1_4_R1.Packet60Explosion;
import net.minecraft.server.v1_4_R1.Packet61WorldEvent;
import net.minecraft.server.v1_4_R1.Packet62NamedSoundEffect;
import net.minecraft.server.v1_4_R1.Packet6SpawnPosition;
import net.minecraft.server.v1_4_R1.Packet70Bed;
import net.minecraft.server.v1_4_R1.Packet71Weather;
import net.minecraft.server.v1_4_R1.Packet7UseEntity;
import net.minecraft.server.v1_4_R1.Packet8UpdateHealth;
import net.minecraft.server.v1_4_R1.Packet9Respawn;

public class CommonPacket {
	private Packet packet;
	private Packets type;
	
	public CommonPacket(int id) {
		this.type = Packets.getFromInt(id);
		this.packet = type.getPacket();
	}
	
	public CommonPacket(Packets packet) {
		this.type = packet;
		this.packet = type.getPacket();
	}
	
	public CommonPacket(Packet packet) {
		String name = packet.getClass().getSimpleName();
		this.type = Packets.getFromInt(countNrs(name));
		this.packet = packet;
	}
	
	public Packets getType() {
		return this.type;
	}
	
	public void write(String field, int count, Object value) {
		try {
			Field data = packet.getClass().getDeclaredField(field);
			data.setAccessible(true);
			data.set(packet, value);
			data.setAccessible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object read(String field) {
		Object obj = null;
		try {
			Field data = packet.getClass().getDeclaredField(field);
			data.setAccessible(true);
			obj = data.get(packet);
			data.setAccessible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	private static int countNrs(String str) {
		String result = "";
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(str);
		
		while(matcher.find()) {
			result += matcher.group();
		}
		
		return result != "" ? Integer.valueOf(result) : 0;
	}
	
	public static enum Packets {
		KEEP_ALIVE(0, new Packet0KeepAlive()),
		OPEN_WINDOW(100, new Packet100OpenWindow()),
		CLODE_WINDOW(101, new Packet101CloseWindow()),
		WINDOW_CLICK(102, new Packet102WindowClick()),
		SET_SLOW(103, new Packet103SetSlot()),
		WINDOW_ITEMS(104, new Packet104WindowItems()),
		PROGRESS_BAR(105, new Packet105CraftProgressBar()),
		TRANSACTION(106, new Packet106Transaction()),
		SET_CREATIVE_SLOT(107, new Packet107SetCreativeSlot()),
		BUTTON_CLICK(108, new Packet108ButtonClick()),
		FLYING(10, new Packet10Flying()),
		PLAYER_POSITION(11, new Packet11PlayerPosition()),
		PLAYER_LOOK(12, new Packet12PlayerLook()),
		UPDATE_SIGN(130, new Packet130UpdateSign()),
		ITEM_DATA(131, new Packet131ItemData()),
		TILE_ENTITY_DATA(132, new Packet132TileEntityData()),
		PLAYER_LOOK_MOVE(13, new Packet13PlayerLookMove()),
		BLOCK_DIG(14, new Packet14BlockDig()),
		PLACE(15, new Packet15Place()),
		BLOCK_ITEM_SWITCH(16, new Packet16BlockItemSwitch()),
		ENTITY_LOCATION_ACTION(17, new Packet17EntityLocationAction()),
		ANIMATION(18, new Packet18ArmAnimation()),
		ENTITY_ACTION(19, new Packet19EntityAction()),
		LOGIN(1, new Packet1Login()),
		STATISTIC(200, new Packet200Statistic()),
		PLAYER_INFO(201, new Packet201PlayerInfo()),
		ABILITIES(202, new Packet202Abilities()),
		TAB_COMPLETE(203, new Packet203TabComplete()),
		CLIENT_INFO(204, new Packet204LocaleAndViewDistance()),
		CLIENT_COMMAND(205, new Packet205ClientCommand()),
		NAMED_ENTITY_SPAWN(20, new Packet20NamedEntitySpawn()),
		COLLECT(22, new Packet22Collect()),
		VEHICLE_SPAWN(23, new Packet23VehicleSpawn()),
		MOB_SPAWN(24, new Packet24MobSpawn()),
		CUSTOM_PAYLOAD(250, new Packet250CustomPayload()),
		KEY_RESPONSE(252, new Packet252KeyResponse()),
		KEY_REQUEST(253, new Packet253KeyRequest()),
		GET_INFO(254, new Packet254GetInfo()),
		DISCONNECT(255, new Packet255KickDisconnect()),
		ENTITY_PAINTING(25, new Packet25EntityPainting()),
		ADD_EXP_ORB(26, new Packet26AddExpOrb()),
		ENTITY_VELOCITY(28, new Packet28EntityVelocity()),
		DESTROY_ENTITY(29, new Packet29DestroyEntity()),
		HANDSHAKE(2, new Packet2Handshake()),
		ENTITY(30, new Packet30Entity()),
		ENTITY_MOVE(31, new Packet31RelEntityMove()),
		ENTITY_LOOK(32, new Packet32EntityLook()),
		ETITY_MOVE_LOOK(33, new Packet33RelEntityMoveLook()),
		ENTITY_TELEPORT(34, new Packet34EntityTeleport()),
		ENTITY_HEAD_ROTATION(35, new Packet35EntityHeadRotation()),
		ENTITY_STATUS(38, new Packet38EntityStatus()),
		ATTACH_ENTITY(39, new Packet39AttachEntity()),
		CHAT(3, new Packet3Chat()),
		ENTITY_METADATA(40, new Packet40EntityMetadata()),
		MOB_EFFECT(41, new Packet41MobEffect()),
		REMOVE_MOB_EFFECT(42, new Packet42RemoveMobEffect()),
		SET_EXP(43, new Packet43SetExperience()),
		UPDATE_TIME(4, new Packet4UpdateTime()),
		MAP_CHUNK(51, new Packet51MapChunk()),
		MULTI_BLOCK_CHANGE(52, new Packet52MultiBlockChange()),
		BLOCK_CHANGE(53, new Packet53BlockChange()),
		PLAY_NOTEBLOCK(54, new Packet54PlayNoteBlock()),
		BLOCK_BREAK_ANIMATION(55, new Packet55BlockBreakAnimation()),
		MAP_CHUNK_BULK(56, new Packet56MapChunkBulk()),
		ENTITY_EQUIPMENT(5, new Packet5EntityEquipment()),
		EXPLOSION(60, new Packet60Explosion()),
		WOLRD_EVENT(61, new Packet61WorldEvent()),
		NAMED_SOUND_EFFECT(62, new Packet62NamedSoundEffect()),
		SPAWN_POSITION(6, new Packet6SpawnPosition()),
		BED(70, new Packet70Bed()),
		WEATHER(71, new Packet71Weather()),
		USE_ENTITY(7, new Packet7UseEntity()),
		UPDATE_HEALTH(8, new Packet8UpdateHealth()),
		RESPAWN(9, new Packet9Respawn());
		
		
		private int id;
		private Packet packet;
		
		Packets(int id, Packet packet) {
			this.id = id;
			this.packet = packet;
		}
		
		public Packet getPacket() {
			return packet;
		}
		
		public static Packets getFromInt(int from) {
			for(Packets p : values()) {
				if(p.id == from)
					return p;
			}
			return null;
		}
	}
}
