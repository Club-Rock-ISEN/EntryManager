package org.clubrockisen.service.format;

import java.util.ArrayList;
import java.util.List;

import org.clubrockisen.common.TranslationKeys;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.abstracts.Converter;
import org.clubrockisen.service.abstracts.Format;

/**
 * Old file format.<br />
 * Sample:
 * 
 * <pre>
 * ﻿Name;mail;F/H;A/M;entries;next free;credit;?;?;§
 * </pre>
 * @author Alex
 */
public final class OldDataFiles implements Format {
	/** Unique instance of the class */
	private static OldDataFiles					singleton	= new OldDataFiles();
	
	/** The list with the field order */
	private final List<Converter<MemberColumn>>	fieldOrder;
	
	/**
	 * Constructor #1.<br />
	 */
	private OldDataFiles () {
		super();
		fieldOrder = new ArrayList<>();
		fieldOrder.add(new Converter<>(MemberColumn.NAME));
		fieldOrder.add(null);
		fieldOrder.add(new Converter<MemberColumn>(MemberColumn.GENDER) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Gender read (final String data) {
				if ("H".equalsIgnoreCase(data)) {
					return Gender.MALE;
				}
				return Gender.FEMALE;
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Converter#write(java.lang.Object)
			 */
			@Override
			public String write (final Object field) {
				if (Gender.MALE.equals(field)) {
					return "H";
				}
				return "F";
			}
		});
		fieldOrder.add(new Converter<MemberColumn>(MemberColumn.STATUS) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Status read (final String data) {
				if ("M".equalsIgnoreCase(data)) {
					return Status.VETERAN;
				}
				return Status.MEMBER;
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Converter#write(java.lang.Object)
			 */
			@Override
			public String write (final Object field) {
				if (Status.VETERAN.equals(field)) {
					return "M";
				}
				return "A";
			}
		});
		fieldOrder.add(new Converter<MemberColumn>(MemberColumn.ENTRIES) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Integer read (final String data) {
				return Integer.valueOf(data);
			}
		});
		fieldOrder.add(new Converter<MemberColumn>(MemberColumn.NEXT_FREE) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Integer read (final String data) {
				return Integer.valueOf(data);
			}
		});
		fieldOrder.add(new Converter<MemberColumn>(MemberColumn.CREDIT) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Double read (final String data) {
				return Double.valueOf(data);
			}
		});
		fieldOrder.add(null);
		fieldOrder.add(null);
	}
	
	/**
	 * Return the unique instance of the class.
	 * @return the singleton.
	 */
	public static OldDataFiles getInstance () {
		return singleton;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.Format#getMemberSeparator()
	 */
	@Override
	public String getEntitySeparator () {
		return "§";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.Format#getFieldSeparator()
	 */
	@Override
	public String getFieldSeparator () {
		return ";";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.Format#getFieldOrder()
	 */
	@Override
	public List<Converter<MemberColumn>> getFieldOrder () {
		return fieldOrder;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return TranslationKeys.FORMATS.oldFileFormat();
	}
	
}
