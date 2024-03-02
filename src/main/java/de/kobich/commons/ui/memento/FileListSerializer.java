package de.kobich.commons.ui.memento;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListSerializer implements IMementoItemSerializer2<List<File>> {
	private static final String FILE_COUNT_POSTFIX = "-FileCount";
	private static final String PATH_POSTFIX = "-Path-";
	private final String stateName;
	
	/**
	 * @param stateName
	 */
	public FileListSerializer(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public List<File> restore(IMementoItem mementoItem) {
		int count = mementoItem.getInteger(stateName + FILE_COUNT_POSTFIX, 0);
		
		List<File> files = new ArrayList<File>(count);
		for (int i = 0; i < count; ++ i) {
			String path = mementoItem.getString(stateName + PATH_POSTFIX + i, "");
			File file = new File(path);
			files.add(file);
		}
		return files;
	}

	@Override
	public void restore(List<File> files, IMementoItem mementoItem) {
		files.addAll(restore(mementoItem));
	}

	@Override
	public void save(List<File> files, IMementoItem mementoItem) {
		mementoItem.putInteger(stateName + FILE_COUNT_POSTFIX, files.size());
		for (int i = 0; i < files.size(); ++ i) {
			File file = files.get(i);
			mementoItem.putString(stateName + PATH_POSTFIX + i, file.getAbsolutePath());
		}
	}

}
