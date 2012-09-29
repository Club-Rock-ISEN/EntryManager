package org.clubrockisen.service.format;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.abstracts.Format;

/**
 * Old file format.<br />
 * Sample:
 * <pre>﻿Name;mail;F/H;A/M;entries;next free;credit;?;?;§</pre>
 * @author Alex
 */
public final class OldDataFiles implements Format {
	/** Logger */
	private static Logger			lg			= Logger.getLogger(OldDataFiles.class.getName());
	
	/** Unique instance of the class */
	private static OldDataFiles		singleton	= new OldDataFiles();
	
	/** The list with the field order */
	private final List<Converter>	fieldOrder;
	
	/**
	 * Constructor #1.<br />
	 */
	private OldDataFiles () {
		super();
		fieldOrder = new ArrayList<>();
		fieldOrder.add(new Converter(MemberColumn.NAME));
		fieldOrder.add(null);
		fieldOrder.add(new Converter(MemberColumn.GENDER) {
			
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Gender convert (final String data) {
				if ("H".equalsIgnoreCase(data)) {
					return Gender.MALE;
				}
				return Gender.FEMALE;
			}
			
		});
		fieldOrder.add(new Converter(MemberColumn.STATUS) {
			
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Status convert (final String data) {
				if ("A".equalsIgnoreCase(data)) {
					return Status.VETERAN;
				}
				return Status.MEMBER;
			}
		});
		fieldOrder.add(new Converter(MemberColumn.ENTRIES) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Integer convert (final String data) {
				return Integer.valueOf(data);
			}
		});
		fieldOrder.add(new Converter(MemberColumn.NEXT_FREE) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Integer convert (final String data) {
				return Integer.valueOf(data);
			}
		});
		fieldOrder.add(new Converter(MemberColumn.CREDIT) {
			/*
			 * (non-Javadoc)
			 * @see org.clubrockisen.service.abstracts.Format.Converter#convert(java.lang.String)
			 */
			@Override
			public Double convert (final String data) {
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
	public String getMemberSeparator () {
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
	public List<Converter> getFieldOrder () {
		return fieldOrder;
	}
}
