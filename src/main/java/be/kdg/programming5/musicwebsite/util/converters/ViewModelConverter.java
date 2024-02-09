package be.kdg.programming5.musicwebsite.util.converters;

public interface ViewModelConverter<ViewClass, ModelClass> {
    ModelClass convertToModel(ViewClass viewClass);
    ViewClass convertToView(ModelClass modelClass);
}
